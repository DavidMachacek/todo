package cz.sgconsulting.be.todo.model

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository: CrudRepository<TodoDTO, Long>