package cz.sgconsulting.be.todo.model

import org.hibernate.annotations.CreationTimestamp
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name="todo")
data class TodoDTO (
        @Id
        @GeneratedValue(strategy= GenerationType.AUTO)
        val id: Int? = null,
        var title: String,
        @CreationTimestamp
        val created: ZonedDateTime? = null
)