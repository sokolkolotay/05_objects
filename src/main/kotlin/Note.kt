data class Note(
    val id: Int,
    var ownerId: Int,
    var title: String,
    var text: String,
    var date: Int,
    var comments: Int,
    var readComments: Int,
    var viewUrl: String,
    var deleted: Boolean, //true "Да" - false "Нет"
    var privacy: Int = 0,
    var commentPrivacy: Int = 0,
    var privacyView: String = "",
    var privacyComment: String = ""
)