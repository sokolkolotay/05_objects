fun main() {

    val wallService = WallService()

    val post = wallService.add(
        Post(
            id = 1,
            ownerId = 1,
            fromId = 123456,
            createdBy = 0,
            date = 0,
            text = "_",
            replyOwnerId = 0,
            replyPostId = 0,
            friendsOnly = false,
            postType = "_",
            signerId = 0,
            copyHistory = null,
            canPin = false,
            canDelete = false,
            canEdit = false,
            isPinned = 0,
            markedAsAds = false,
            isFavorite = false,
            postponedId = 0,
            postSource = null,
            geo = null,
            donut = null,
            repost = null,
            views = null,
            copyright = null,
            likes = null,
            comments = null,
            attachments = emptyList()
        )
    )

    val comment = Comment(
        id = 1,
        fromId = 1,
        date = 0,
        text = "",
        donut = Comment.Donut(
            isDon = false,
            placeholder = ""
        ),
        replyToUser = 0,
        replyToComment = 0,
        attachments = PhotoAttachments(
            photo = Photo(
                id = 0,
                albumId = 0,
                ownerId = 0,
                userId = 0,
                text = "",
                date = 0,
                photo130 = "",
                photo604 = ""
            )
        ),
        parentsStack = emptyList(),
        thread = Comment.Thread(
            count = 0,
            items = emptyList(),
            canPost = false,
            showReplyButton = false,
            groupsCanPost = false
        )
    )

    val addedComment = wallService.createComment(post.id, comment)
    println("Комментарий успешно добавлен: $addedComment")
}

