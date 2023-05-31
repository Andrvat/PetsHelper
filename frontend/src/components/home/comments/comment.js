import CommentForm from "./comment_form";
import userImg from "../../../images/user_photo.jpg"
import "./comments_index.css"

const Comment = ({
                     comment,
                     replies,
                     setActiveComment,
                     activeComment,
                     updateComment,
                     deleteComment,
                     addComment,
                     parentId = null,
                     currentUserId,
                 }) => {
    const isEditing =
        activeComment &&
        activeComment.id === comment.id &&
        activeComment.type === "editing";
    const isReplying =
        activeComment &&
        activeComment.id === comment.id &&
        activeComment.type === "replying";
    const fiveMinutes = 300000;
    const timePassed = new Date() - new Date(comment.date) > fiveMinutes;
    const canDelete =
        localStorage.getItem("username") === comment.authorUserName && replies.length === 0 && !timePassed;
    const canReply = Boolean(currentUserId);
    const canEdit = localStorage.getItem("username") === comment.authorUserName && !timePassed;
    const replyId = parentId ? parentId : comment.id;
    const createdAt = new Date(comment.date).toLocaleDateString();
    return (
        <div key={comment.id} className="comment">
            <div className="comment-image-container">
                {/*<img style={{marginLeft: '2rem', width: '3rem', height: '3rem', objectFit: 'cover'}} src={userImg} />*/}
                <img src={userImg} />
            </div>
            <div className="comment-right-part">
                <div className="comment-content">
                    <div className="comment-author">{comment.authorUserName}</div>
                    <div className="comment-date">{createdAt}</div>
                </div>
                {!isEditing && <div className="comment-text">{comment.text}</div>}
                {isEditing && (
                    <CommentForm
                        submitLabel="Update"
                        hasCancelButton
                        initialText={comment.text}
                        handleSubmit={(text) => updateComment(text, comment.id)}
                        handleCancel={() => {
                            setActiveComment(null);
                        }}
                    />
                )}
                <div className="comment-actions">
                    {canReply && (
                        <div
                            className="comment-action"
                            onClick={() =>
                                setActiveComment({ id: comment.id, type: "replying" })
                            }
                        >
                            Reply
                        </div>
                    )}
                    {canEdit && (
                        <div
                            className="comment-action"
                            onClick={() =>
                                setActiveComment({ id: comment.id, type: "editing" })
                            }
                        >
                            Edit
                        </div>
                    )}
                    {canDelete && (
                        <div
                            className="comment-action"
                            onClick={() => deleteComment(comment.id)}
                        >
                            Delete
                        </div>
                    )}
                </div>
                {isReplying && (
                    <CommentForm
                        submitLabel="Reply"
                        handleSubmit={(text) => addComment(text, replyId)}
                    />
                )}
                {replies.length > 0 && (
                    <div className="replies">
                        {replies.map((reply) => (
                            <Comment
                                comment={reply}
                                key={reply.id}
                                setActiveComment={setActiveComment}
                                activeComment={activeComment}
                                updateComment={updateComment}
                                deleteComment={deleteComment}
                                addComment={addComment}
                                parentId={comment.id}
                                replies={[]}
                                currentUserId={currentUserId}
                            />
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
};

export default Comment;