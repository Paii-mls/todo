# Todo Application Sample using Spring Boot
This is my first simple todo app for a spring boot project

## Exposed methods
### Todo API

**1. Get Todo by id. (Http Method: GET)**
```
http://localhost:8080/api/todos/{todo_id}
```

**2. Get All Todo. (Http Method: GET)**
```
http://localhost:8080/api/todos
```

**3. Create Todo. (Http Method: POST)**
```
http://localhost:8080/api/todos
```

**4. Update Todo. (Http Method: PUT)**
```
http://localhost:8080/api/todos/{todo_id}
```

**5. Delete Todo. (Http Method: DELETE)**
```
http://localhost:8080/api/todos/{todo_id}
```

### Todo Item API

**1. Create Todo Item. (Http Method: POST)**
```
http://localhost:8080/api/todos/{todo_id}/items
```

**2. Update Todo Item. (Http Method: PUT)**
```
http://localhost:8080/api/todos/items/{id}
```

**3. Delete Todo Item. (Http Method: DELETE)**
```
http://localhost:8080/api/todos/items/{id}
```
