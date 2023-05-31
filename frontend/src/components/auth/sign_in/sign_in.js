import * as React from "react";
import { Navigate } from "react-router";
import "./sign_in.css";
import {ACTUAL_BACKEND_ADDRESS} from "../../../AppConstants";

export default class SignIn extends React.Component {
  state = {
    email: "",
    password: "",
    isAuthorized: false,
  };

  constructor(props) {
    super(props);
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event) {
    const target = event.target;
    const name = target.name;
    this.setState({ [name]: target.value });
  }

  async handleSubmit() {
    console.log("Отправленное имя:", this.state.email);

    let address_final = ACTUAL_BACKEND_ADDRESS + "auth/";
    const response = await fetch(address_final, {
      method: "POST", // *GET, POST, PUT, DELETE, etc.
      mode: "cors", // no-cors, cors, *same-origin
// cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
// credentials: "same-origin", // include, *same-origin, omit
      headers: {
        "Content-Type": "application/json",
// "Content-Type": "application/x-www-form-urlencoded",
      },
      redirect: "follow", // manual, *follow, error
      referrer: "no-referrer", // no-referrer, *client
      body: JSON.stringify(this.state), // body data type must match "Content-Type" header
    });

    if (!response.ok) throw new Error(response.status);

    const data = await response.json();
    if (data.status) {
      this.jwt = data.jwt;
      this.setState({isAuthorized: true});
      console.log("jwt", this.jwt);
      localStorage.setItem("jwt",this.jwt);
      localStorage.setItem("username", data.username);
      localStorage.setItem("isAdmin", data.isAdmin);
      console.log("isAdmin:", data.isAdmin);
      // localStorage.setItem("isAdmin", true);
    } else {
      alert("Wrong email or password. Try again");
    }
  }

  render() {
    let { isAuthorized } = this.state;
    return (
      <div>
        {isAuthorized && <Navigate to="/post/all/all" replace={true} />}

        <h3>Sign In</h3>

        <div className="mb-3">
          <label>Email address</label>
          <input
            name="email"
            type="email"
            className="form-control"
            placeholder="Enter email"
            value={this.state.email}
            onChange={this.handleChange}
          />
        </div>

        <div className="mb-3">
          <label>Password</label>
          <input
            name="password"
            type="password"
            className="form-control sm"
            placeholder="Enter password"
            value={this.state.password}
            onChange={this.handleChange}
          />
        </div>

        <div className="d-grid">
          <button className="btn btn-primary" onClick={this.handleSubmit}>
            Submit
          </button>
        </div>
      </div>
    );
  }
}
