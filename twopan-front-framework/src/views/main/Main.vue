<template>
    <div>
        <div class="top">
            <div class="top-op">
                <div class="btn">
                    <el-upload
                      :show-file-list="false"
                      :with-credentials="true"
                      :multiple="true"
                      :http-request="addFile"
                      :accept="fileAccept"
                      >
                        <el-button type="primary">
                            <span class="iconfont icon-upload"></span>
                            上传
                        </el-button>
                    </el-upload>
                </div>
                <el-button type="sucess">
                    <span class="iconfont icon-folder-add"></span>
                    新建文件夹
                </el-button>
                <el-button type="danger">
                    <span class="iconfont icon-del"></span>
                    批量删除
                </el-button>
                <el-button type="warning">
                    <span class="iconfont icon-del"></span>
                    批量移动
                </el-button>
                <div class="search-panel">
                    <el-input clearable placeholder="提示信息" >
                        <template #suffix>
                            <i class="iconfont icon-search"></i>
                        </template>
                    </el-input>
                </div>
                <div class="iconfont icon-refresh"></div>
            </div>
            <div>全部文件</div>
        </div>
        <div class="file-list">
            <!-- <Table 
              ref="dataTableRef" 
              :columns="columns"
              
              :dataSource="tableData"
              :fetch="loadDataList"
              :initFetch="true"
              :options="tableOptions"
              @rowSelected="rowSelected">
            </Table> -->
            <Table 
                ref="dataTableRef" 
                :columns="columns"
                :dataSource="tableData"
                :fetch="loadDataList"
                :initFetch="true"
                :options="tableOptions"
                @rowSelected="rowSelected">
            </Table>

        </div>
    </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick } from "vue";
const { proxy } = getCurrentInstance();

const api = {
    loadDataList: "/file/loadDataList",
    rename: "/file/rename",
    newFolder: "/file/newFolder",
    getFolderInfo: "/file/getFolderInfo",
    delFile: "/file/delFile",
    changeFileFolder: "/file/changeFileFolder",
    createDownloadUrl: "/file/createDownloadUrl",
    download: "/api/file/download"
};
const columns = [
    {
        label: "文件名",
        prop: "fileName",
        scopedSlots: "fileName",
    },
    {
        label: "修改时间",
        prop: "lastUpdateTime",
        width: 200,
    },
    {
        label: "大小",
        prop: "fileSize",
        scopedSlots: "fileSize",
        width: 200,
    },
];

const tableData = ref({});
const tableOptions = ref({
    extHeight: 50,
    selectType: "checkbox",
});

const fileNameFuzzy = ref();
const categroy = ref();

const loadDataList = async () => {
    let params = {
        pageNo: tableData.value.pageNo,
        pageSize: tableData.value.pageSize,
        fileNameFuzzy: fileNameFuzzy.value,
        filePid: 0,
    };
    if (params.categroy !== "all") {
        delete params.filePid;
    }
    let result = await proxy.Request({
        url:api.loadDataList,
        params:params,
    });
    if (!result) {
        return;
    }
    tableData.value = result.data;
};

// async loadDataList() {
//     let params = {
//         pageNo: this.tableData.pageNo,
//         pageSize: this.tableData.pageSize,
//         fileNameFuzzy: this.fileNameFuzzy,
//         filePid: 0,
//     };
//     if (params.categroy !== "all") {
//         delete params.filePid;
//     }
//     let result = await this.proxy.Request({
//         url: this.api.loadDataList,
//         params: params,
//     });
//     if (!result) {
//         return;
//     }
//     // 使用 $nextTick 确保更新发生在 DOM 更新之后
//     this.$nextTick(() => {
//         this.tableData = result.data;
//     });
// }


const rowSelected = () => {};
</script>

<style lang="scss" scoped>

@import "../../assets/file.list.scss";
</style>

