import { useState, useEffect } from "react";
import CommentForm from "./comment_form";
import Comment from "./comment";
import {
    getComments as getCommentsApi,
    createComment as createCommentApi,
    updateComment as updateCommentApi,
    deleteComment as deleteCommentApi,
} from "./comments_api";
import {Card} from "react-bootstrap";
import {ACTUAL_BACKEND_ADDRESS} from "../../../AppConstants";

const Comments = ({ commentsUrl, currentUserId, postId}) => {
    const [backendComments, setBackendComments] = useState([]);
    const [activeComment, setActiveComment] = useState(null);
    const rootComments = backendComments.filter(
        (backendComment) => backendComment.parentId === null
    );
    const getReplies = (commentId) =>
        backendComments
            .filter((backendComment) => backendComment.parentId === commentId)
            .sort(
                (a, b) =>
                     new Date(a.date).getTime() - new Date(b.date).getTime()
            );
    const addComment = (text, parentId) => {
        console.log(postId)
        console.log(activeComment)
        console.log("BACKEND:")
        console.log(backendComments)
        console.log("ROOT")
        console.log(rootComments)
        createCommentApi(text, parentId).then(async (comment) => {
            comment.postId = postId.postId;
            console.log(comment)

            const response = await fetch(ACTUAL_BACKEND_ADDRESS + "comment/create", {
                method: "POST",
                mode: "cors",
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("jwt"),
                    "Content-Type": "application/json",
                },
                redirect: "follow",
                referrer: "no-referrer",
                body: JSON.stringify(comment),
            });

            if (!response.ok) throw new Error(response.status);

            const data = await response.json();
            console.log(data);
            comment.id = data
            setBackendComments([comment, ...backendComments]);
            setActiveComment(null);
        });
    };

    const updateComment = (text, commentId) => {
        updateCommentApi(text).then(async () => {
            console.log("MAP");
            const updatedBackendComments = backendComments.map((backendComment) => {
                console.log(backendComment.id + "---" + commentId)
                if (backendComment.id === commentId) {
                    return {...backendComment, text: text};
                }
                return backendComment;
            });
            setBackendComments(updatedBackendComments);
            setActiveComment(null);

            let request = {id: commentId, text: text}

            const response = await fetch(ACTUAL_BACKEND_ADDRESS + "comment/update/", {
                method: "POST",
                mode: "cors",
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("jwt"),
                    "Content-Type": "application/json",
                },
                redirect: "follow",
                referrer: "no-referrer",
                body: JSON.stringify(request),
            });

            if (!response.ok) throw new Error(response.status);

            const data = await response.json();
            console.log(data);

        });
    };
    const deleteComment = async (commentId) => {
        if (window.confirm("Are you sure you want to remove comment?")) {
            deleteCommentApi().then(() => {
                const updatedBackendComments = backendComments.filter(
                    (backendComment) => backendComment.id !== commentId
                );
                setBackendComments(updatedBackendComments);
            });
        }

        const response = await fetch(ACTUAL_BACKEND_ADDRESS + "comment/delete/" + commentId, {
            method: "POST",
            mode: "cors",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("jwt"),
                "Content-Type": "application/json",
            },
            redirect: "follow",
            referrer: "no-referrer"
        });

        if (!response.ok) throw new Error(response.status);

    };

    useEffect(() => {
        getCommentsApi(postId).then((data) => {
            setBackendComments(data);
        });
    }, [postId]);

    return (
        <Card style={{marginTop: "2rem", marginBottom: "2rem"}}>
        <div className="comments">
            <h2 style={{margin: "2rem"}}>Комментарии</h2>
            <CommentForm submitLabel="Отправить" handleSubmit={addComment} />

            <div className="comments-container">
                {rootComments.map((rootComment) => (
                    <Comment
                        key={rootComment.id}
                        comment={rootComment}
                        replies={getReplies(rootComment.id)}
                        activeComment={activeComment}
                        setActiveComment={setActiveComment}
                        addComment={addComment}
                        deleteComment={deleteComment}
                        updateComment={updateComment}
                        currentUserId={currentUserId}
                        postId={postId}
                    />
                ))}
            </div>
        </div>
        </Card>
    );
};

export default Comments;