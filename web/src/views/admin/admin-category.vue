<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <p>
        <a-form
            layout="inline"
            :model="param"
        >
          <a-form-item>
            <a-input size="large" v-model:value="param.name" placeholder="查询内容"></a-input>
          </a-form-item>
          <a-form-item>
            <a-button
                type="primary"
                size="large"
                @click="handleQuery({page: 1,size: pagination.pageSize})"
            >
              查询
            </a-button>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="add()" size="large">
              新增
            </a-button>
          </a-form-item>
        </a-form>
      </p>
      <a-table
          :columns="columns"
          :row-key="record => record.id"
          :data-source="categorys"
          :pagination="pagination"
          :loading="loading"
          @change="handleTableChange"
      >
        <template v-slot:action="{ text, record }">
          <a-space size="small">
            <a-button type="primary" @click="edit(record)">
              编辑
            </a-button>
            <a-popconfirm
                title="删除后不可恢复，确认删除?"
                ok-text="是"
                cancel-text="否"
                @confirm="handleDelete(record.id)"
            >
              <a-button type="danger">
                删除
              </a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>

  <a-modal
      title="分类表单"
      v-model:visible="modalVisible"
      :confirm-loading="modalLoading"
      @ok="handleModalOk"
  >

    <a-form :model="category" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <a-form-item label="名称">
        <a-input v-model:value="category.name"/>
      </a-form-item>
      <a-form-item label="父分类">
        <a-input v-model:value="category.parent"/>
      </a-form-item>
      <a-form-item label="顺序">
        <a-input v-model:value="category.sort"/>
      </a-form-item>
    </a-form>

  </a-modal>

</template>


<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import axios from 'axios';
import {message} from "ant-design-vue";
import {Tool} from "@/util/tool";

export default defineComponent({
  name: 'AdminCategory',
  setup() {
    const param = ref();
    param.value = {};

    //首行属性列表
    const columns = [
      {
        title: '名称',
        dataIndex: 'name'
      },
      {
        title: '父分类',
        key: 'parent',
        dataIndex: 'parent'
      },
      {
        title: '顺序',
        dateIndex: 'sort'
      },
      {
        title: '操作',
        key: 'action',
        slots: {customRender: 'action'}
      }
    ];

    //显示缓冲效果
    const loading = ref(false);

    //查询到的分类列表
    const categorys = ref();


    //分页相关变量
    const pagination = ref({
      current: 1,
      pageSize: 10,
      total: 0
    });

    //查询函数
    const handleQuery = (params: any) => {
      loading.value = true;
      axios.get("/category/list", {
        params: {
          page: params.page,
          size: params.size,
          name: param.value.name,
        }
      }).then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success) {
          categorys.value = data.content.list;
  /*        categorys.value=[
            {
              "id": 100,
              "name": "Vue",
              "parent": 101,
              "sort": 100,
            },
            {
              "id": 100,
              "name": "React",
              "parent": 101,
              "sort": 100,
            }
          ]*/
          // 重置分页按钮
          pagination.value.current = params.page;
          pagination.value.total = data.content.total;
        } else {
          message.error(data.message,3);
        }
      });
    };

    //页码跳转
    const handleTableChange = (pagination: any) => {
      handleQuery({
        page: pagination.current,
        size: pagination.pageSize
      });
    };

    //表单
    const category = ref({});

    //是否显示
    const modalVisible = ref(false);

    //是否缓冲
    const modalLoading = ref(false);

    //确认按钮，2秒结束
    const handleModalOk = () => {
      modalLoading.value = true;
      axios.post("/category/save", category.value).then((response) => {
        const data = response.data;
        modalLoading.value = false;
        if (data.success) {
          modalVisible.value = false;
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize
          });
        } else {
          message.error(data.message,3);
        }
      });
    };

    //删除功能
    const handleDelete = (id: string) => {
      axios.delete("/category/delete/" + id).then((response) => {
        const data = response.data;
        modalLoading.value = false;
        if (data.success) {
          modalVisible.value = false;
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize
          });
        } else {
          message.error(data.message,3);
        }
      });
    };

    //编辑按钮
    const edit = (record: any) => {
      modalVisible.value = true;
      category.value = Tool.copy(record);
    };

    //新增按钮
    const add = () => {
      modalVisible.value = true;
      category.value = {};
    };

    //生命周期函数，页面一打开执行的函数
    onMounted(() => {
      handleQuery({
        page: 1,
        size: pagination.value.pageSize
      });
    });


    //返回参数
    return {
      //变量
      categorys,
      pagination,
      columns,
      category,
      loading,
      modalVisible,
      modalLoading,
      param,
      //函数
      handleTableChange,
      edit,
      handleModalOk,
      add,
      handleDelete,
      handleQuery,
    }
  },

});
</script>