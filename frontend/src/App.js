import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import {BrowserRouter as Router, Routes, Route } from "react-router-dom";
import HomePage from "./components/home/home_page";
import UserPage from "./components/user/user_page/user_page";
import AuthPage from "./components/auth/auth_page";
import {Navigate} from "react-router";

function App() {
    return (
        <Router>
            <Routes>
                <Route exact path="/" element={<Navigate to="/auth"/>}/>
                <Route exact path="/auth/*" element={<AuthPage/>}/>
                <Route exact path="/post/*" element={<HomePage/>}/>
                <Route exact path="/user/*" element={<UserPage/>}/>
            </Routes>
        </Router>
    );
}

export default App;
