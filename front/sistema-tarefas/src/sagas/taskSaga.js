import { put, takeLatest, call } from 'redux-saga/effects';
import {
  FETCH_TASKS,
  ADD_TASK,
  UPDATE_TASK,
  DELETE_TASK,
  MARK_TASK_COMPLETED,
  fetchTasksSuccess,
  fetchTasksFailure,
  addTaskSuccess,
  addTaskFailure,
  updateTaskSuccess,
  updateTaskFailure,
  deleteTaskSuccess,
  deleteTaskFailure,
  markTaskCompletedSuccess,
  markTaskCompletedFailure,
} from '../actions/taskActions';

const API_BASE_URL = 'http://localhost:8080';

const fetchTasksFromApi = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/tarefas`);
    const data = await response.json();
    console.log("data", data)
    return data;
  } catch (error) {
    console.error('Error fetching tasks:', error);
    throw error;
  }
};

const addTaskToApi = async (task) => {

  try {
    const response = await fetch(`${API_BASE_URL}/tarefas`, {
      method: 'POST',
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(
        { 
          descricao: task.title,
          status: task.completed? "CONCLUIDO" : "PENDENTE"
      }
      )
    }
    )
    const data = new FormData()
    data.append("file",task.photoPath)
    const responseBody = await response.json()

    const response2 = await fetch(`${API_BASE_URL}/tarefas/${responseBody.id}/imagem`, {
      method: 'POST',
      body: data
    }
    )
    return response; 
  } catch (error) {
    console.error('Error ao adicionar tarefa', error);
    throw error;
  }
  
};


const updateTaskToApi = async (task) => {
  console.log("title: ", task.title)
  console.log("completed: ", task.completed)


  try {
    const formData = new FormData();

    const response = await fetch(`${API_BASE_URL}/tarefas/${task.id}`, {
      method: 'PUT',
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(
        { 
          descricao: task.title,
          status: task.completed? "CONCLUIDO" : "PENDENTE"
      }
      )
    });

    console.log("data UPDATE", response);
    return response;
  } catch (error) {
    console.error('Error updating task:', error);
    throw error;
  }
};



const deleteTaskFromApi = async (taskId) => {
  try {
    const response = await fetch(`${API_BASE_URL}/tarefas/${taskId}`, {
      method: 'DELETE',
    });
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error deleting task:', error);
    throw error;
  }
};

const markTaskCompletedInApi = async (taskId) => {
  try {
    const response = await fetch(`${API_BASE_URL}/tarefas/${taskId}/concluir`, {
      method: 'GET',
    });
    const data = await response.json();
    console.log(response)
    return data;
  } catch (error) {
    console.error('Não foi possivel concluir a  tarefa:', error);
    throw error;
  }
};

function* fetchTasksSaga() {
  try {
    const tasks = yield call(fetchTasksFromApi);
    yield put(fetchTasksSuccess(tasks));
  } catch (error) {
    yield put(fetchTasksFailure(error.message || 'Erro ao obter tarefas.'));
  }
}

function* addTaskSaga(action) {
  try {
    const newTask = yield call(addTaskToApi, action.payload);
    yield put(addTaskSuccess(newTask));
  } catch (error) {
    yield put(addTaskFailure(error.message || 'Erro ao adicionar tarefa.'));
  }
}

function* updateTaskSaga(action) {
  try {
    const updatedTask = yield call(updateTaskToApi, action.payload);
    yield put(updateTaskSuccess(updatedTask));
  } catch (error) {
    yield put(updateTaskFailure(error.message || 'Erro ao atualizar tarefa.'));
  }
}

function* deleteTaskSaga(action) {
  try {
    const deletedTaskId = yield call(deleteTaskFromApi, action.payload);
    yield put(deleteTaskSuccess(deletedTaskId));
  } catch (error) {
    yield put(deleteTaskFailure(error.message || 'Erro ao excluir tarefa.'));
  }
}

function* markTaskCompletedSaga(action) {
  try {
    const markedTaskId = yield call(markTaskCompletedInApi, action.payload);
    yield put(markTaskCompletedSuccess(markedTaskId));
  } catch (error) {
    yield put(markTaskCompletedFailure(error.message || 'Erro ao marcar tarefa como concluída.'));
  }
}

function* taskSaga() {
  yield takeLatest(FETCH_TASKS, fetchTasksSaga);
  yield takeLatest(ADD_TASK, addTaskSaga);
  yield takeLatest(UPDATE_TASK, updateTaskSaga);
  yield takeLatest(DELETE_TASK, deleteTaskSaga);
  yield takeLatest(MARK_TASK_COMPLETED, markTaskCompletedSaga);
}

export default taskSaga;
