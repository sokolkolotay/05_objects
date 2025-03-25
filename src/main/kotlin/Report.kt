//Задача№3 "Жалобы" темы "07_exception"
data class Report(
    val commentId: Int,
    val reason: ReportReason
)

enum class ReportReason(val reportId: Int) {
    SPAM(0),
    CHILD_PORNOGRAPHY(1),
    EXTREMISM(2),
    VIOLENCE(3),
    DRUG_PROPAGANDA(4),
    ADULT_CONTENT(5),
    ABUSE(6),
    SUICIDE_CALLS(8);
}