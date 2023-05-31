import React from "react";
import ReactDOM from "react-dom";
import CommentsApp from "./comments_app";
import "./comments_index.css";

ReactDOM.render(
    <React.StrictMode>
        <CommentsApp/>
    </React.StrictMode>,
    document.getElementById("root")
);