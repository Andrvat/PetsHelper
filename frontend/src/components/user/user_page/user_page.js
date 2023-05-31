import React, {Component} from 'react'
// import '../sign_in/sign_in.css'
import {Container} from "react-bootstrap";
import HomeNavbar from "./user_navbar";
import UserProfile from "./user_profile";
import {Routes} from "react-router";
import {Route} from "react-router-dom";
import PostGrid from "../../home/post_grid";
import Post from "../../home/post/post";
import UserNavbar from "./user_navbar";
import ProfileEditor from "./profile_edit";
import PostCreator from "../../home/post/post_create";
import PostEditor from "../../home/post/post_edit";
import UsersList from "./subscribers_list";

export default class UserPage extends Component {
    render() {
        return (
            <Container>
                <UserNavbar/>
                <Routes>
                    <Route exact path="/:username" element={<UserProfile />}/>
                    <Route exact path="/:username/edit" element={<ProfileEditor />} />
                    <Route exact path="/:username/:subs" element={<UsersList />} />
                    <Route exact path="/post/create" element={<PostCreator />} />
                    <Route exact path="/post/edit/:postId" element={<PostEditor/>} />
                </Routes>
            </Container>
        )
    }
}