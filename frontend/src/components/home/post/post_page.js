import React, {Component} from 'react'
import {Container} from "react-bootstrap";
import CommentsApp from "../comments/comments_app";
import Post from "./post";
import HomeNavbar from "../home_navbar";

export default class PostPage extends Component {
    render() {
        return (
            <Container>
                <HomeNavbar/>
                <Post postId={25}/>
                {/*<CommentsApp/>*/}
            </Container>
        )
    }
}