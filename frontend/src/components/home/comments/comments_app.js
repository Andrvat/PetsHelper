import Comments from "./comments";

const CommentsApp = (postId) => {
    console.log("POST ID:")
    console.log(postId)
    return (
        <div>
            <Comments
                commentsUrl="http://localhost/comments"
                currentUserId="1"
                postId={postId}
            />
        </div>
    );
};

export default CommentsApp;