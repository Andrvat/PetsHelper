import {ACTUAL_BACKEND_ADDRESS} from "../../../AppConstants";

export const getComments = async (postId) => {
    console.log("GET:")
    console.log(postId)
    const response = await fetch(ACTUAL_BACKEND_ADDRESS + "comment/all/" + postId.postId, {
            method: "GET",
            mode: "cors",
            headers: {
                "Content-Type": "application/json",
            },

            redirect: "follow",
            referrer: "no-referrer",
        }
    );

    if (!response.ok) throw new Error(response.status);

    const data = await response.json();
    console.log("DATA")
    console.log(data)
    return data;
};

export const createComment = async (text, parentId = null) => {
    return {
        text: text,
        parentId: parentId,
        authorUserName: localStorage.getItem("username"),
        date: new Date().toISOString(),
    };
};

export const updateComment = async (text) => {
    return { text };
};

export const deleteComment = async () => {
    return {};
};