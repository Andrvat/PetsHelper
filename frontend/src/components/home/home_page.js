import React, { Component } from "react";
import { Container } from "react-bootstrap";
import "./home_page.css";
import { Routes } from "react-router";
import { Route } from "react-router-dom";
import PostGrid from "./post_grid";
import Post from "./post/post";
import HomeNavbar from "./home_navbar";
import ModeratedPosts from "./moderate_posts";

export default class HomePage extends Component {
  render() {
    return (
      <Container>
        <HomeNavbar />
        <Routes>
          <Route path="/:section/:category" element={<PostGrid />} />
          <Route path="/id/:postId" element={<Post />} />
          <Route path="/moderate/all" element={<ModeratedPosts />} />
        </Routes>
      </Container>
    );
  }
}

