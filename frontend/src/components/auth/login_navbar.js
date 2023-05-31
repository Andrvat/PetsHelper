import { Link } from "react-router-dom";
import React, { Component } from "react";

export default class LoginNavbar extends Component {
  render() {
    return (
      <nav className="navbar navbar-expand-lg navbar-light">
        <div className="container w-100">
          <Link className="navbar-toggle-icon" to="sign-in">
            <div>
              <img
                className="m-lg-auto"
                src="/images/cat_1.svg"
                width="40px"
                alt="icon"
              />
            </div>
          </Link>
          <Link className="navbar-brand px-4" to="/auth">
            PetsHelper
          </Link>
          <div
            className="collapse navbar-collapse d-flex flex-row-reverse"
            id="navbarTogglerDemo02"
          >
            <ul className="navbar-nav">
              <li className="nav-item mx-4">
                <Link className="nav-link" to="./sign-in">
                  Sign in
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="./sign-up">
                  Sign up
                </Link>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    );
  }
}
