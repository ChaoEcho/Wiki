<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <p>
        <a-button type="primary" @click="add()" size="large">
          新增
        </a-button>
      </p>
      <a-table
          :columns="columns"
          :row-key="record => record.id"
          :data-source="ebooks"
          :pagination="pagination"
          :loading="loading"
          @change="handleTableChange"
      >
        <template #cover="{ text: cover }">
          <a-avatar v-if="cover" :src="cover" alt="avatar" />
        </template>
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
      title="电子书表单"
      v-model:visible="modalVisible"
      :confirm-loading="modalLoading"
      @ok="handleModalOk"
  >

    <a-form :model="ebook" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <a-form-item label="封面">
        <a-input v-model:value="ebook.cover" />
      </a-form-item>
      <a-form-item label="名称">
        <a-input v-model:value="ebook.name" />
      </a-form-item>
      <a-form-item label="分类">
        <a-cascader
            v-model:value="categoryIds"
            :field-names="{ label: 'name', value: 'id', children: 'children' }"
            :options="level1"
        />
      </a-form-item>
      <a-form-item label="描述">
        <a-input v-model:value="ebook.description" type="textarea" />
      </a-form-item>
    </a-form>

  </a-modal>

</template>


<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import axios from 'axios';
import {message} from "ant-design-vue";
export default defineComponent({
  name: 'AdminEbook',
  setup() {
    //首行属性列表
    const columns = [
      {
        title: '封面',
        dataIndex: 'cover',
        slots: { customRender: 'cover' }
      },
      {
        title: '名称',
        dataIndex: 'name'
      },
      {
        title: '分类一',
        key: 'category1Id',
        dateIndex: 'category1Id'
      },
      {
        title: '分类二',
        dateIndex: 'category2Id'
      },
      {
        title: '文档数',
        dataIndex: 'docCount'
      },
      {
        title: '阅读数',
        dataIndex: 'viewCount'
      },
      {
        title: '点赞数',
        dataIndex: 'voteCount'
      },
      {
        title: '操作',
        key: 'action',
        slots: { customRender: 'action' }
      }
    ];

    //显示缓冲效果
    const loading = ref(false);

    //查询到的电子书列表
    const ebooks = ref();

    //分页相关变量
    const pagination = ref({
      current: 1,
      pageSize: 4,
      total: 0
    });

    //查询函数
    const handleQuery = (params: any) => {
      loading.value = true;
      axios.get("/ebook/list", {
        params: {
          page: params.page,
          size: params.size
        }
      }).then((response)=>{
        loading.value = false;
        const data = response.data;
        if(data.success){
          ebooks.value = data.content.list;
          // 重置分页按钮
          pagination.value.current = params.page;
          pagination.value.total = data.content.total;
        }else {
          message.error(data.message);
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
    const ebook = ref({});

    //是否显示
    const modalVisible = ref(false);

    //是否缓冲
    const modalLoading = ref(false);

    //确认按钮，2秒结束
    const handleModalOk = () => {
      modalLoading.value = true;
      axios.post("/ebook/save", ebook.value).then((response)=>{
        const data = response.data;
        modalLoading.value=false;
        if(data.success){
          modalVisible.value=false;
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize
          });
        }else {
          message.error(data.message);
        }
      });
    };

    //删除功能
    const handleDelete = (id: string) => {
      axios.delete("/ebook/delete/"+id).then((response)=>{
        const data = response.data;
        modalLoading.value=false;
        if(data.success){
          modalVisible.value=false;
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize
          });
        }else {
          message.error(data.message);
        }
      });
    };

    //编辑按钮
    const edit = (record: any) => {
      modalVisible.value = true;
      ebook.value = record;
    };

    //新增按钮
    const add = () => {
      modalVisible.value = true;
      ebook.value = {};
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
      ebooks,
      pagination,
      columns,
      handleTableChange,
      edit,
      ebook,
      loading,
      modalVisible,
      modalLoading,
      handleModalOk,
      add,
      handleDelete,
    }
  }
});
</script>

<style scoped>
  .ant-avatar{
    width: 50px;
    height: 50px;
    line-height: 50px;
    border-radius: 8%;
    margin: 5px 0;
  }
</style>