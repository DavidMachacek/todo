package cz.sgconsulting.be.todo.rest

import cz.sgconsulting.be.todo.model.TodoDTO
import cz.sgconsulting.be.todo.model.TodoRepository
import cz.sgconsulting.be.todo.model.UpdateTodoDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*", "http://todo.apps.ocsp1.sg-lab.local"], allowedHeaders = ["*"])
@RequestMapping("/todo")
@RestController
class TodoController(
        val repo: TodoRepository
) {

    val logger: Logger = LoggerFactory.getLogger(TodoController::class.java)

    @GetMapping
    fun getTodos(): List<TodoDTO> {
        logger.info("request=[REST]/todo, action=getTodosBegin")
        return repo.findAll().toList().also {
            logger.info("action=getTodosEnd")
        }
    }

    @PostMapping
    fun postTodo(@RequestBody todo: TodoDTO): TodoDTO {
        logger.info("request=[REST]/todo, action=postTodoBegin")
        return repo.save(todo).also {
            logger.info("action=postTodoEnd")
        }
    }

    @GetMapping("/{id}")
    fun getTodo(@PathVariable (value="id") id: Long): TodoDTO {
        logger.info("request=[REST]/todo/{id}, action=getTodoBegin", id)
        return repo.findById(id.toLong()).orElseThrow().also {
            logger.info("action=getTodoEnd")
        }
    }

    @DeleteMapping("/{id}")
    fun deleteTodo(@PathVariable (value="id") id: Long) {
        logger.info("request=[REST]/todo/{id}, action=deleteTodoBegin", id)
        return repo.deleteById(id).also {
            logger.info("action=deleteTodoEnd")
        }
    }

    @PutMapping("/{id}")
    fun updateTodo(@PathVariable (value="id") id: Long, @RequestBody todo: UpdateTodoDTO): TodoDTO {
        logger.info("request=[REST]/todo/{id}, action=updateTodoBegin", id)
        return repo.findById(id).orElseThrow().apply { title = todo.title}.also {
            logger.info("action=updateTodoEnd")
        }
    }
}