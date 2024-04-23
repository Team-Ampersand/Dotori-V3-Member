package ampersand.userservice.persistence

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "tbl_member")
class MemberEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long = 0,

    @field:NotNull
    val name: String,

    @field:NotNull
    val email: String,

    @field:NotNull
    val password: String,

    @Column(columnDefinition = "TINYINT")
    val grade: Int?,

    @Column(name = "class_number", columnDefinition = "TINYINT")
    val classNum: Int?,

    @Column(columnDefinition = "TINYINT")
    val number: Int?,

    @Column(name = "profile_image")
    var profileImage: String?,

    @field:NotNull
    @field:Enumerated(EnumType.STRING)
    val authority: Authority,

    @field:NotNull
    @field:Enumerated(EnumType.STRING)
    var selfStudyStatus: SelfStudyStatus,

    @field:NotNull
    @field:Enumerated(EnumType.STRING)
    var massageStatus: MassageStatus
) {
    fun applySelfStudy() {
        this.selfStudyStatus = SelfStudyStatus.CANT
    }

    fun cancelSelfStudy() {
        this.selfStudyStatus = SelfStudyStatus.CAN
    }

    fun applyMassage() {
        this.massageStatus = MassageStatus.CANT
    }

    fun cancelMassage() {
        this.massageStatus = MassageStatus.CAN
    }
}