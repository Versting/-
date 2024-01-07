package com.twotwo;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.twotwo.client.OssClient;
import com.twotwo.common.R;
import com.twotwo.common.UserContext;
import com.twotwo.controller.*;
import com.twotwo.entity.*;
import com.twotwo.request.RegisterRequest;
import com.twotwo.request.UpdatePasswordRequest;
import com.twotwo.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class ServiceTests {
    @Resource
    private DirectoryService directoryService;
    @Resource
    private FileService fileService;
    @Resource
    private FileSharingService fileSharingService;
    @Resource
    private MessageService messageService;
    @Resource
    private OperationService operationService;
    @Resource
    private UserService userService;
    @Resource
    private DirectoryController directoryController;
    @Resource
    private UserController userController;
    @Resource
    private FileController fileController;
    @Resource
    private OperationController operationController;
    @Resource
    private DepartmentController departmentController;
    @Resource
    private OssClient ossClient;
    //	@Resource
    private OSS oss;

    @BeforeEach
    public void init() {
        oss = new OSSClientBuilder().build("oss-cn-guangzhou.aliyuncs.com", "LTAI5tNK13Nrjg3xBQt3VAEj", "ZNK5h7IwuIfFpMC1sOUFWVpy94hqKu");
    }

    @Test
    public void test(){
        User user = userService.getById(1);
        UserContext.setUser(user);
        userService.getOrgDirectory();
    }

    @Test
    void login(){
        User user=new User();
        user.setEmail("1234567899@qq.com");
        user.setPassword("555");
        String res=HttpUtil.post("localhost:8000/user/login",JSONUtil.toJsonStr(user));
        System.out.println(res);
    }

    @Test
    void md5() {
        String mimeType = HttpUtil.getMimeType("1.txt");
        System.out.println(mimeType);
    }

    @Test
    void name() {
        // 通过设置 delimiter 参数来模拟列举文件夹
        ObjectListing objectListing = oss.listObjects("cloud-disk-api", "user");
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(objectSummary.getKey());
        }
    }

    @Test
    void upload() {
        ObjectListing objectListing = oss.listObjects("cloud-disk-api");
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(objectSummary.getKey());
        }
//		oss.putObject("cloud-disk-api", "user/application.yaml", FileUtil.file("application.yaml"));
        oss.shutdown();
    }







    /*
        @Test
        public void testGetOne() {
            // 假设存在一个用户的id为"1"
            String userId = "1";

            R<User> result = userController.getOne(userId);

            // 打印测试结果
            System.out.println("测试结果：" + result);
        }
    */
    @Test
    public void testSendEmail() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("2738898085@qq.com");
        userController.sendEmail(request);
        System.out.print("ok");
    }



    @Test
    public void testAudit() throws Exception {
        String userId = "1";
        userController.audit(userId);
        R<User> result = userController.getOne(userId);
        System.out.println("测试结果：" + result);
        System.out.print("ok");

    }
    @Test
    void testUpdatePassword() {
        // 准备测试数据
        UpdatePasswordRequest request = new UpdatePasswordRequest();
        request.setOldPassword("555");
        request.setNewPasswordAgain("111");
        request.setNewPassword("111");
        userController.updatePassword(request);
    }

    @Test
    public void testSetAdmin() {
        // 准备测试数据
        String id = "1";
        userController.setAdmin(id);
    }

    @Test
    public void testresetAdmin() {
        // 准备测试数据
        String id = "1";
        userController.resetAdmin(id);
    }
    @Test
    public void testDeleteFailed() {
        // 准备测试数据
        String id = "2";
        userController.delete(id);
    }
    /*@Test
    public void testlist() {
        String company="123";
        R<List<User>> u=userController.list(company);
        System.out.print(u);
    }*/
    @Test
    void testlogin() {
        User user = new User();
        user.setEmail("1234567899@qq.com");
        user.setPassword("555");
        String res = HttpUtil.post("localhost:8000/user/login", JSONUtil.toJsonStr(user));
        System.out.println(res);
    }

    @Test
    void movetest(){
        // 准备测试数据
        String sourcIid = "1";
        String targeIid = "4";

        // 调用move方法
        directoryController.move(sourcIid, targeIid);

    }

	/*@Test
	void savetest(){
		Directory a=new Directory();
		Date n=new Date();
		a.setDepartmentId(1);
		a.setId(5);
		a.setName("mzj7");
		a.setParentId(1);
		a.setCreateTime(n);
		directoryController.save(a);

	}*/

    @Test
    void savetest(){
        Directory a=new Directory();
        a.setId(6);
        a.setName("mzj8");
        directoryController.update(a);

    }
    @Test
    public void testuserlist() {
        String company="123";
        R<List<User>> u=userController.list(company);
        System.out.print(u);
    }
    /*@Test
    void listtest(){
        directoryController.list("mzj2");
    }*/

    @Test
    void update() throws IOException {
        User user=new User();
        user.setId(1);
        user.setEmail("123456789999@qq.com");
        user.setUsername("yonghu");
        user.setCompanyId(122222);
        user.setDepartmentId(12344444);
        MultipartFile file=null;
        userController.update(user,file);
    }
    @Test
    void register() throws IOException {
        RegisterRequest user=new RegisterRequest();
        user.setCode("123");
        user.setPassword("Aa123456");
        user.setEmail("1234567@qq.com");
        MultipartFile file=null;
        userController.register(user,file);
    }

    @Test
    public void testlist() {
        String company="1";
        R<List<User>> u=userController.list(company);
        System.out.print(u);
    }
    /*@Test
    public void testregister throws IOException(){
        RegisterRequest request=new RegisterRequest();
        request.setCode("123");
        request.setEmail("3059571374@qq.com");
        request.setPassword("111");
        userController.register(request);
    }*/
    @Test
    public void testfind(){
        String department="0";
        R<List<File>>u=fileController.find(department);
        System.out.println(u);
    }

    @Test
    public void testgeturl(){
        int id=13;
        System.out.println(fileController.share(id));
    }


    @Test
    public void testoperationlist() {
        // 准备测试数据
        String id="2";
        R<List<Operation>> u =operationController.list(id);
        System.out.print(u);
    }

  /*  @Test
    public void testoperationsave() {
        // 准备测试数据
        User user = new User();
        user.setId(1);
        UserContext.setUser(user);
        Operation operation = new Operation();
        Date now = new Date();
        operation.setId(9);
        operation.setUserId(1);
        operation.setDepartmentid(2);
        operation.setContent("下载文件2333");
        operation.setFileId(123);
        operation.setCreateTime(now);

        System.out.print(operationController.save(operation));
    }
*/







    @Test
    public void testdepartmentgetone() {
        // 准备测试数据
        String id="1";
        R<Department> u = departmentController.getOne(id);
        System.out.print(u);
    }
    @Test
    public void testdepartmentlist() {
        // 准备测试数据
        String id="2";
        R<List<Department>> u = departmentController.list(id);
        System.out.print(u);
    }
    @Test
    public void testdown()throws IOException{
        File file = fileService.getById(20);
        byte[] bytes = ossClient.downloadFile(file.getUrl());
        String text = new String(bytes);
        System.out.println(text);
    }
}