import React, { Component } from "react";
import { Routes, Route } from "react-router-dom";
import { Card } from "react-bootstrap"

import LoginNavbar from "./login_navbar";
import SignIn from "./sign_in/sign_in";
import SignUp from "./sign_up/sign_up";
import SignUpPrivate from "./sign_up/sign_up_private";
import SignUpOrganization from "./sign_up/sign_up_organization";
import HomePage from "../home/home_page";

export default class AuthPage extends Component {
  render() {
    return (
      <div className="h-100 w-100 d-flex flex-column">
        <LoginNavbar />
        <div className="flex-fill d-flex justify-content-center align-items-center">
            <Card className="p-3">
                <Routes>
                    <Route exact path="/" element={<SignIn />} />
                    <Route exact path="/sign-in" element={<SignIn />} />
                    <Route exact path="/sign-up" element={<SignUp />} />
                    <Route exact path="/sign-up/private" element={<SignUpPrivate />} />
                    <Route exact path="/sign-up/organization" element={<SignUpOrganization />} />
                </Routes>
            </Card>
        </div>
      </div>
    );
  }
}
