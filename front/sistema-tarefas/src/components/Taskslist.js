import React, { useEffect, useState } from 'react';
import { Table, Space, Button, Tag, Modal, message, Empty } from 'antd';
import { EditOutlined, DeleteOutlined, PictureOutlined, UnorderedListOutlined, InboxOutlined } from '@ant-design/icons';
import { connect } from 'react-redux';
import { fetchTasks, deleteTask, markTaskCompleted } from '../actions/taskActions';
import EditTaskModal from './EditTaskModal';

const TaskList = ({ tasks, error, fetchTasks, deleteTask, markTaskCompleted }) => {
  const [editModalVisible, setEditModalVisible] = useState(false);
  const [viewImageModalVisible, setViewImageModalVisible] = useState(false);
  const [selectedImage, setSelectedImage] = useState('');
  const [selectedTaskKey, setSelectedTaskKey] = useState(null);

  useEffect(() => {
    fetchTasks();
  }, [fetchTasks]);

  const dataSource = tasks
    ? tasks.map((task) => ({
      key: task.id,
      task: task.descricao,
      status: task.status,
      image: task.photoPath, 
    }))
    : [];

  const columns = [
    {
      title: 'Tarefa',
      dataIndex: 'task',
      key: 'task',
    },
    {
      title: 'Status',
      dataIndex: 'status',
      key: 'status',
      render: (status) => (
        <Tag color={status === 'CONCLUIDO' ? 'green' : 'orange'}>
          {status}
        </Tag>
      ),
    },
    {
      key: 'image',
      render: (_, record) => (
        <Button
          icon={<PictureOutlined />}
          onClick={() => handleViewImage(`http://localhost:8080/tarefas/${record.key}/imagem`)}
        >
          Ver
        </Button>
      ),
    },
    {
      key: 'actions',
      render: (_, record) => (
        <Space size="middle">
          <Button type="primary" icon={<EditOutlined />} onClick={() => handleEditTask(record.key)}>
          </Button>
          <Button
            onClick={() => handleComplete(record.key, record.status === 'Concluído')}
            style={{
              left:'50px',
              backgroundColor: record.status === 'CONCLUIDO' ? 'Chartreuse' : null,
              color: record.status === 'CONLCUIDO' ? 'white' : null,
            }}
          >
            {record.status === 'CONCLUIDO' ? 'Concluído' : 'Concluir'}
          </Button>
        </Space>
      ),
    },
  ];

  const handleEditTask = (key) => {
    setEditModalVisible(true);
    setSelectedTaskKey(key);
  };

  const handleComplete = (key, isCompleted) => {
    if (!isCompleted) {
      markTaskCompleted(key);
      setTimeout(() => {
        fetchTasks();
      }, 500);
      message.success('Tarefa marcada como concluída!');
    } else {
      message.warning('Esta tarefa já está concluída.');
    }
  };

  const handleViewImage = (imageUrl) => {
    if (imageUrl) {
      setSelectedImage(imageUrl);
      setViewImageModalVisible(true);
    } else {
      message.warning('Não há imagem para exibir. Adicione uma imagem à tarefa.');
    }
  };

  const closeEditModal = () => {
    setEditModalVisible(false);
    setSelectedTaskKey(null);
  };

  const closeViewImageModal = () => {
    setViewImageModalVisible(false);
    setSelectedImage('');
  };

  return (
    <>
      {dataSource.length === 0 ? (
        <Empty
          image={<InboxOutlined style={{ marginTop: '33px', fontSize: 60, color: '#000000E0 ' }} />}
          description={<span>Não há tarefas </span>}
        />
      ) : (
        <Table
          dataSource={dataSource}
          columns={columns}
          bordered
          title={() => <h2 style={{ fontSize: '25px' }}>Tarefas<UnorderedListOutlined /> </h2>}
          pagination={{ pageSize: 4 }}
          style={{ backgroundColor: '#fff', borderRadius: '8px' }}
        />
      )}
      <Modal
        visible={viewImageModalVisible}
        onCancel={closeViewImageModal}
        footer={null}
        width={800}
      >
        {selectedImage && <img src={selectedImage} alt="Imagem da Tarefa" style={{ width: '80%', height: '100%' }} />}
      </Modal>
      <EditTaskModal
        visible={editModalVisible}
        onCancel={closeEditModal}
        taskKey={selectedTaskKey}
      />
    </>
  );
};

const mapStateToProps = (state) => ({
  tasks: state.tasks,
  error: state.tasks,
});

const mapDispatchToProps = (dispatch) => ({
  fetchTasks: () => dispatch(fetchTasks()),
  deleteTask: (taskId) => dispatch(deleteTask(taskId)),
  markTaskCompleted: (taskId) => dispatch(markTaskCompleted(taskId)),
});

export default connect(mapStateToProps, mapDispatchToProps)(TaskList);
