package ampersand.userservice.persistence

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import java.util.UUID

@Entity
@Table(name = "tbl_user")
class UserEntity(
    @Id
    @Column(columnDefinition = "BINARY(16)")
    val id: UUID,
)