const fetchData = {
    getAll: () =>
      fetch("http://localhost:8080/tarefas").then((resp) => resp.json()),
  };
  
  export { fetchData };