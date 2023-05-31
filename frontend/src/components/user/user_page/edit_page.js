import {Component} from "react";
import {Container} from "react-bootstrap";
import ProfileEditor from "./profile_edit";
import UserNavbar from "./user_navbar";

export default class ProfileEditPage extends Component {
    render() {
        return (
            <Container>
                <UserNavbar/>
                <ProfileEditor/>
            </Container>
        )
    }
}