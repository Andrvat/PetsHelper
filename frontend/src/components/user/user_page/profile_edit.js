import {Component} from "react";
import UserProfile from "./user_profile";
import PostPage from "../../home/post/post_page";
import {Button, Card, Col, Container, FormControl, InputGroup, Row} from "react-bootstrap";
import HomeNavbar from "./user_navbar";
import cardImg from "../../../images/user_photo.jpg";
import React from "react";
import {ACTUAL_BACKEND_ADDRESS} from "../../../AppConstants";

export default class ProfileEditor extends Component {

    state = {
        id: undefined,
        username: "undefined",
        firstName: "undefined",
        lastName: "undefined",
        email: " - ",
        phoneNumber: " - ",
        city: "undefined",
        gender: "undefined",
        aboutMe: "undefined",
    }

    constructor(props) {
        console.log("HERE")
        super(props);
        this.handleChange=this.handleChange.bind(this)
        this.handleClick = this.handleClick.bind(this)
    }

    componentDidMount() {
        this.getData();
    }

    async getData() {
        console.log("Username" + localStorage.getItem("username"))
        const response = await fetch(ACTUAL_BACKEND_ADDRESS + "personality/username/" + localStorage.getItem("username"), {
            method: "GET",
            mode: "cors",
            headers: {
                "Content-Type": "application/json",
            },
            redirect: "follow",
            referrer: "no-referrer",
        });

        if (!response.ok) throw new Error(response.status);

        const data = await response.json();
        console.log(data)
        this.setState({id: data.id})
        this.setState({username: data.username})
        this.setState({firstName: data.firstName})
        this.setState({lastName: data.lastName})
        this.setState({aboutMe: data.aboutMe})
        this.setState({email: data.email})
        this.setState({phoneNumber: data.phoneNumber})
        this.setState({city: data.city})
        this.setState({gender: data.gender})
    }

    handleChange(event) {
        const target = event.target;
        const name = target.name;
        this.setState({ [name]: target.value });
    }

    async handleClick() {
        console.log(this.state)
        const response = await fetch(ACTUAL_BACKEND_ADDRESS + "personality/update", {
            method: "POST",
            mode: "cors",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("jwt"),
                "Content-Type": "application/json",
            },
            redirect: "follow",
            referrer: "no-referrer",
            body: JSON.stringify(this.state),
        });

        if (!response.ok) throw new Error(response.status);
        console.log(response)
        const data = await response.json();
        this.setState({[this.state.id]: data})
        localStorage.setItem("username",this.state.username)
        console.log(data);
        //return <Redirect to={"post-page"}/>
    }

    render() {
        return (
            <Card style={{width: 'auto', marginTop: '7rem'}}>
                <Row className='no-gutters'>
                    <Col md={5} lg={5}>
                        <Card.Img variant="top" src={cardImg} style={{
                            marginTop: '4rem',
                            marginLeft: '2rem',
                            width: '500px',
                            height: '500px',
                            objectFit: 'cover'
                        }}/>
                        <Button variant="primary" style={{
                            width: '500px',
                            marginTop: '2rem',
                            marginLeft: '2rem',
                            backgroundColor: '#5cd0c5'
                        }}>Обновить фото профиля</Button>

                    </Col>
                    <Col style={{margin: '20px 20px 20px 11px'}}>
                        <Card.Body>
                            <div className='mx-4'>
                                <Card.Subtitle style={{marginTop: '20px', marginBottom: "6px"}}>Никнейм:</Card.Subtitle>
                                <Card.Text>
                                    <InputGroup className="mb-3">
                                        <InputGroup.Text style={{width: "44px"}}>@</InputGroup.Text>
                                        <FormControl placeholder={this.state.username} name="username" onChange={this.handleChange}/>
                                    </InputGroup>
                                </Card.Text>

                                <Card.Subtitle style={{marginTop: '20px', marginBottom: "6px"}}>Фамилия:</Card.Subtitle>
                                <Card.Text>
                                    <InputGroup className="mb-3">
                                        <InputGroup.Text style={{width: "44px"}}>✔</InputGroup.Text>
                                        <FormControl value={this.state.lastName} name="lastName" onChange={this.handleChange}/>
                                    </InputGroup>
                                </Card.Text>

                                <Card.Subtitle style={{marginTop: '20px', marginBottom: "6px"}}>Имя:</Card.Subtitle>
                                <Card.Text>
                                    <InputGroup className="mb-3">
                                        <InputGroup.Text style={{width: "44px"}}>✔</InputGroup.Text>
                                        <FormControl value={this.state.firstName} name="firstName" onChange={this.handleChange}/>
                                    </InputGroup>
                                </Card.Text>

                                <Card.Subtitle style={{marginTop: '20px', marginBottom: "6px"}}>Пол:</Card.Subtitle>
                                <Card.Text>
                                    <InputGroup className="mb-3">
                                        <InputGroup.Text style={{width: "44px"}}>👤</InputGroup.Text>
                                        <FormControl value={this.state.gender} name="gender" onChange={this.handleChange}/>
                                    </InputGroup>
                                </Card.Text>

                                <Card.Subtitle style={{marginTop: '20px', marginBottom: "6px"} }>Город:</Card.Subtitle>
                                <Card.Text>
                                    <InputGroup className="mb-3">
                                        <InputGroup.Text style={{width: "44px"}}>🏙</InputGroup.Text>
                                        <FormControl value={this.state.city} name="city" onChange={this.handleChange}/>
                                    </InputGroup>
                                </Card.Text>

                                <Card.Subtitle style={{marginTop: '20px', marginBottom: "6px"}}>Почта:</Card.Subtitle>
                                <Card.Text>
                                    <InputGroup className="mb-3">
                                        <InputGroup.Text style={{width: "44px"}}>✉</InputGroup.Text>
                                        <FormControl value={this.state.email} name="email" onChange={this.handleChange}/>
                                    </InputGroup>
                                </Card.Text>

                                <Card.Subtitle style={{marginTop: '20px', marginBottom: "6px"}}>Номер
                                    телефона:</Card.Subtitle>
                                <Card.Text>
                                    <InputGroup className="mb-3">
                                        <InputGroup.Text style={{width: "44px"}}>☎</InputGroup.Text>
                                        <FormControl value={this.state.phoneNumber} name="phoneNumber" onChange={this.handleChange}/>
                                    </InputGroup>
                                </Card.Text>

                                <Card.Subtitle style={{marginTop: '20px', marginBottom: "6px"}}>Обо мне:</Card.Subtitle>
                                <Card.Text>
                                    <InputGroup className="mb-3">
                                        <InputGroup.Text style={{width: "44px"}}>☆</InputGroup.Text>
                                        <FormControl as="textarea" value={this.state.aboutMe} name="aboutMe" onChange={this.handleChange}/>
                                    </InputGroup>
                                </Card.Text>
                            </div>
                        </Card.Body>
                    </Col>
                </Row>

                <Card.Body>
                    <Col>
                        <Button variant="primary" onClick={this.handleClick} style={{
                            width: "100%",
                            backgroundColor: '#5cd0c5'
                        }}>Сохранить изменения</Button>
                    </Col>
                    <Col>
                        <Button variant="primary" style={{
                            width: "100%",
                            marginTop: '1rem',
                            marginBottom: '1rem',
                            backgroundColor: '#5cd0c5'
                        }}>Отменить изменения</Button>
                    </Col>
                </Card.Body>
            </Card>
        )
    }
}