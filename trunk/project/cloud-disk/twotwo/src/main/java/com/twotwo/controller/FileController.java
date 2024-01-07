package com.twotwo.controller;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.http.HttpUtil;
import com.twotwo.client.OssClient;
import com.twotwo.common.R;
import com.twotwo.entity.Directory;
import com.twotwo.entity.File;
import com.twotwo.service.FileService;
import com.twotwo.service.OperationService;
import com.twotwo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.twotwo.entity.Operation;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/file")
@Api(tags = "管理网盘文件接口")
public class FileController {
    @Resource
    private OssClient ossClient;
    @Resource
    private UserService userService;
    @Resource
    private FileService fileService;
    @Resource
    private OperationService operationService;

    @GetMapping("/recycleBin")
    @ApiOperation("获取回收站文件")
    public R<List<File>> getRecycleBin() {
        List<Integer> ids = userService.getOrgDirectory().stream().map(Directory::getId).collect(Collectors.toList());
        if (ids.isEmpty()) {
            return R.success(ListUtil.empty());
        }
        return R.success(fileService.lambdaQuery().eq(File::getIsDeleted, 1).in(File::getDirectoryId, ids).list());
    }

    @PostMapping("/recovery/{id}")
    @ApiOperation("恢复网盘文件")
    public R<String> recovery(@PathVariable String id) {
        fileService.lambdaUpdate().eq(File::getId, id).set(File::getIsDeleted, 0).update();
        return R.success();
    }

    @GetMapping("/preview")
    @ApiOperation("预览网盘文件")
    public void preview(@RequestParam String id, HttpServletResponse response) throws IOException {
        File file = fileService.getById(id);
        String mimeType = HttpUtil.getMimeType(file.getUrl());

        String fileExtension = FilenameUtils.getExtension(file.getName());
        int fileType = -1;

        switch (fileExtension.toLowerCase()) {
            case "mp4": case "avi": case "mov": case "wmv": case "flv": case "mpeg":
                fileType = 1; // 视频
                break;
            case "mp3": case "wav": case "ogg": case "flac":
                fileType = 2; // 音频
                break;
            case "doc": case "docx": case "odt": case "rtf":
                fileType = 3; // 文档
                break;
            case "jpg": case "jpeg": case "png": case "gif": case "bmp":
                fileType = 4; // 图片
                break;
            default:
                fileType = 5; // 其他
        }

        response.setContentType(mimeType);
        response.setHeader("File-Type", String.valueOf(fileType));
        byte[] bytes = ossClient.downloadFile(file.getUrl());
        response.getOutputStream().write(bytes);
    }

    @GetMapping("/download/{id}")
    @ApiOperation("下载网盘文件")
    public R<String> download(@PathVariable Integer fileid,Integer userid) {  ////////////////
        File file = fileService.getById(fileid);
        // 添加下载日志
        Operation operation = new Operation();
        operation.setDepartment_id(file.getDirectoryId());///////////////
        operation.setUserId(userid); ///////////////////
        Date date=new Date();//////////////////
        operation.setCreateTime(date);//////////////////
        operation.setContent("下载"+file.getName()+"文件");
        operation.setFileId(fileid);
        operationService.save(operation);
        return R.success(ossClient.getUrl(file.getUrl()));
    }


    @PostMapping("/upload")
    @ApiOperation("上传网盘文件")
    public R<String> upload(@RequestParam("file") MultipartFile multipartFile, File file) throws IOException{
        Date n=new Date();
        String url = ossClient.upload(multipartFile);
        //System.out.println(file.getDirectoryId());
        file.setUrl(url);
        file.setSize(multipartFile.getSize());
        file.setCreateTime(n);
        file.setIsDeleted(0);
        fileService.save(file);
        // 添加下载日志
        Operation operation = new Operation();
        operation.setDepartment_id(file.getDirectoryId());///////////////
        operation.setUserId(file.getUserId()); ///////////////////
        Date date=new Date();//////////////////
        operation.setCreateTime(date);//////////////////
        operation.setContent("上传"+file.getName()+"文件");
        operation.setFileId(file.getId());
        operationService.save(operation);
        return R.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除网盘文件")
    public R<String> delete(@RequestParam String id) {
        //System.out.println(id);
        File file = fileService.getById(id);
        fileService.lambdaUpdate().eq(File::getId, id).set(File::getIsDeleted, 1).update();
        //上传日志
        Operation operations = new Operation();
        operations.setDepartment_id(file.getDirectoryId());///////
        operations.setUserId(file.getUserId()); ///////////////////
        Date date=new Date();//////////////////
        operations.setCreateTime(date);//////////////////
        operations.setContent("删除"+file.getName()+"文件");
        operations.setFileId(file.getId());
        operationService.save(operations);
        return R.success();
    }
    @PostMapping("/batch")
    @ApiOperation("批量删除网盘文件")
    public R<String> deleteBatch(@RequestParam List<String> ids) {
        //System.out.println("111");
        //System.out.println(ids);
        for (String id : ids) {
            //System.out.println(id);
            File file = fileService.getById(id);
            //添加网盘日志
            Operation operations = new Operation();
            operations.setDepartment_id(file.getDirectoryId());
            operations.setUserId(file.getUserId()); ///////////////////
            Date date=new Date();//////////////////
            operations.setCreateTime(date);//////////////////
            operations.setContent("删除"+file.getName()+"文件");
            operations.setFileId(file.getId());
            operationService.save(operations);
        }
        fileService.lambdaUpdate().in(File::getId, ids).set(File::getIsDeleted, 1).update();
        return R.success();
    }

    @PutMapping
    @ApiOperation("修改网盘文件")
    public R<String> update(@RequestBody File file) {
        if (fileService.updateById(file)) {
            return R.success();
        }
        return R.error("更新失败!");
    }
    @ApiOperation("查询部门的所有文件")
    @GetMapping("/find")
    public R<List<File>> find(@RequestParam String company) {
        //System.out.println(company);
        //System.out.println(fileService.getFile(company));
            return R.success(fileService.getFile(company));

    }

    @ApiOperation("获取分享链接")
    @GetMapping("/share")
    public String share(@RequestParam Integer fileid){
        File file=fileService.getById(fileid);
        return(ossClient.getUrl(file.getUrl()));
    }

    @ApiOperation("查询部门回收站文件")
    @GetMapping("/recycle")
    public R<List<File>> recycle( @RequestParam String company) {
        System.out.println(company);
        if(!company.equals("0")) {
            System.out.println(fileService.getByDeFile(company));
            return R.success(fileService.getByDeFile(company));

        }
        else{
            return R.success(fileService.getListFile());
        }
    }

//	@ApiOperation("查询目录下网盘文件")
//	@GetMapping("/list/{directoryId}")
//	public R<List<File>> list(@PathVariable String directoryId) {
//		return R.success(fileService.lambdaQuery().eq(File::getIsDeleted, 0).eq(File::getDirectoryId, directoryId).list());
//	}

}