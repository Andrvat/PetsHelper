import {Component} from "react";
import {
    Button,
    ButtonGroup,
    Card,
    Col,
    Container,
    Form,
    FormControl,
    FormSelect, FormText,
    InputGroup,
    Row
} from "react-bootstrap";
import cardImg from "../../../images/user_photo.jpg";
import React from "react";
import {Navigate} from "react-router";
import {ACTUAL_BACKEND_ADDRESS} from "../../../AppConstants";

export default class PostCreator extends Component {

    // TODO: поправить поля в state
    state = {
        id: "",
        header: "",
        description: "",
        section: "Объявления",
        category: "Отдам в добрые руки",
        tags: "",
        isCreated: false,
        file_1: null,
        file_2: null,
        file_3: null,
        // selectedFile: undefined,
    };

    constructor(props) {
        super(props);
        this.changeSelect = this.changeSelect.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
        this.fileSelectedHandler = this.fileSelectedHandler.bind(this)
        this.fileDeletedHandler = this.fileDeletedHandler.bind(this)
    }

    changeSelect(event) {
        const target = event.target;
        const name = target.name;
        this.setState({[name]: target.value});

        if (name === "section") {
            this.setState({
                category:
                    target.value === "Объявления" ? "Отдам в добрые руки"
                        : target.value === "Мероприятия" ? "Конкурсы"
                            : target.value === "Вопросы-ответы" ? "Ветеринария"
                                : target.value === "Организации" ? "Благотворительные фонды" : ""
            });
        }

    }

    async handleSubmit() {
        console.log("STATE:")
        console.log(localStorage.getItem("jtw"))
        const response = await fetch(ACTUAL_BACKEND_ADDRESS + "post/create", {
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

        const data = await response.json();
        this.setState({id: data})
        console.log(data);
    }


    // fileSelectedHandler= event => {
    //     // this.setState({selectedFiles: this.state.selectedFiles.concat([event.target.files[0]])})
    //     // this.setState({selectedFiles: [...this.state.selectedFiles, event.target.files[0]]})
    //     this.setState({selectedFile: event.target.files[0]})
    // }

    fileSelectedHandler = event => {
        console.log("NAME = " + event.target.name)
        // this.setState({selectedFiles: this.state.selectedFiles.concat([event.target.files[0]])})
        // this.setState({selectedFiles: [...this.state.selectedFiles, event.target.files[0]]})
        this.setState({[event.target.name]: event.target.files[0]})
    }

    fileDeletedHandler = event => {
        this.setState({[event.target.name]: null})
    }

    render() {
        // console.log("Секция: ", this.state.section);
        // console.log("Категория: ", this.state.category);

        console.log("Post id = ", this.state.id);
        console.log("file_1 = ", this.state.file_1);
        console.log("file_2 = ", this.state.file_2);
        console.log("file_3 = ", this.state.file_3);

        return (
            <div>
                {!!this.state.id ? <Navigate to={"/post/id/" + this.state.id}/> :
                    <Card style={{width: 'auto', marginTop: '7rem'}}>
                        <Row className='no-gutters'>

                            <Col style={{margin: '20px 20px 20px 11px'}}>
                                <Card.Body>
                                    <div className='mx-4'>
                                        <Card.Subtitle style={{marginTop: '20px', marginBottom: "6px"}}>Заголовок
                                            объявления:</Card.Subtitle>
                                        <Card.Text>
                                            <input className="mb-3 w-100" maxLength="40" name="header"
                                                   value={this.state.header}
                                                   onChange={this.changeSelect} style={{
                                                height: "2rem",
                                                borderColor: "#cccccc",
                                                borderTopColor: "#ffffff",
                                                borderLeftColor: "#ffffff",
                                                borderRadius: "4px",
                                                borderWidth: "1px"
                                            }}
                                            >
                                            </input>
                                        </Card.Text>

                                        <Card.Subtitle
                                            style={{marginTop: '20px', marginBottom: "6px"}}>Описание:</Card.Subtitle>
                                        <Card.Text>
                                            <InputGroup className="mb-3">
                                                <FormControl as="textarea" name="description"
                                                             onChange={this.changeSelect}
                                                             value={this.state.description}/>
                                            </InputGroup>
                                        </Card.Text>

                                        <Card.Subtitle
                                            style={{marginTop: '20px', marginBottom: "6px"}}>Секция:</Card.Subtitle>
                                        <Card.Text>
                                            <InputGroup list="browsers" name="browser" id="browser" className="mb-3">
                                                <FormSelect name="section" value={this.state.section}
                                                            onChange={this.changeSelect}>
                                                    <option>Объявления</option>
                                                    <option>Мероприятия</option>
                                                    <option>Вопросы-ответы</option>
                                                    <option>Организации</option>
                                                </FormSelect>
                                            </InputGroup>
                                        </Card.Text>

                                        {this.state.section === "Объявления"
                                            ?
                                            <div>
                                                <Card.Subtitle
                                                    style={{
                                                        marginTop: '20px',
                                                        marginBottom: "6px"
                                                    }}>Категория:</Card.Subtitle>
                                                <Card.Text>
                                                    <InputGroup className="mb-3">
                                                        <FormSelect name="category" value={this.state.category}
                                                                    onChange={this.changeSelect}>
                                                            <option>Отдам в добрые руки</option>
                                                            <option>Возьму в добрые руки</option>
                                                            <option>Потеряшки</option>
                                                            <option>Передержка/Перевозка</option>
                                                            <option>Сбор средств</option>
                                                            <option>Товары</option>
                                                            <option>Услуги</option>
                                                        </FormSelect>
                                                    </InputGroup>
                                                </Card.Text>
                                            </div>
                                            : this.state.section === "Мероприятия"
                                                ? <div>
                                                    <Card.Subtitle style={{
                                                        marginTop: '20px',
                                                        marginBottom: "6px"
                                                    }}>Категория:</Card.Subtitle>
                                                    <Card.Text>
                                                        <InputGroup list="browsers" name="browser" id="browser"
                                                                    className="mb-3">
                                                            <FormSelect name="category" value={this.state.category}
                                                                        onChange={this.changeSelect}>
                                                                <option>Конкурсы</option>
                                                                <option>Выставки</option>
                                                            </FormSelect>
                                                        </InputGroup>
                                                    </Card.Text>
                                                </div>
                                                : this.state.section === "Вопросы-ответы"
                                                    ? <div>
                                                        <Card.Subtitle style={{
                                                            marginTop: '20px',
                                                            marginBottom: "6px"
                                                        }}>Категория:</Card.Subtitle>
                                                        <Card.Text>
                                                            <InputGroup list="browsers" name="browser" id="browser"
                                                                        className="mb-3">
                                                                <FormSelect name="category" value={this.state.category}
                                                                            onChange={this.changeSelect}>
                                                                    <option>Ветеринария</option>
                                                                    <option>Содержание</option>
                                                                    <option>Поведение и воспитание</option>
                                                                    <option>Продолжение рода</option>
                                                                </FormSelect>
                                                            </InputGroup>
                                                        </Card.Text>
                                                    </div>
                                                    : this.state.section === "Организации"
                                                        ? <div>
                                                            <Card.Subtitle style={{
                                                                marginTop: '20px',
                                                                marginBottom: "6px"
                                                            }}>Категория:</Card.Subtitle>
                                                            <Card.Text>
                                                                <InputGroup list="browsers" name="browser" id="browser"
                                                                            className="mb-3">
                                                                    <FormSelect name="category"
                                                                                value={this.state.category}
                                                                                onChange={this.changeSelect}>
                                                                        <option>Благотворительные фонды</option>
                                                                        <option>Питомники</option>
                                                                        <option>Приюты</option>
                                                                    </FormSelect>
                                                                </InputGroup>
                                                            </Card.Text>
                                                        </div> : null}

                                        <Card.Subtitle
                                            style={{marginTop: '20px', marginBottom: "6px"}}>Теги:</Card.Subtitle>
                                        <Card.Text>
                                            <InputGroup className="mb-3">
                                                <FormControl placeholder="Например, #кошки #отдам_даром #возьми_меня"
                                                             value={this.state.tags} name="tags"
                                                             onChange={this.changeSelect}/>
                                            </InputGroup>
                                        </Card.Text>

                                        <Card.Subtitle
                                            style={{marginTop: '20px', marginBottom: "6px"}}>Прикрепить
                                            фото:</Card.Subtitle>
                                        {/*<input type="file" onChange={this.fileSelectedHandler} style={{*/}
                                        {/*    marginTop: '1rem',*/}
                                        {/*    marginBottom: '3rem'}}/>*/}

                                        <Card.Text>
                                            <InputGroup className="mb-3">
                                                <FormControl he type="file"
                                                             name="file_1"
                                                             onChange={this.fileSelectedHandler}/>
                                                <Button name="file_1" style={{
                                                    width: "40px",
                                                    display: "flex",
                                                    justifyContent: "center"
                                                }} onClick={this.fileDeletedHandler}>❌</Button>
                                            </InputGroup>
                                            <InputGroup className="mb-3">
                                                <FormControl type="file"
                                                             name="file_2"
                                                             onChange={this.fileSelectedHandler}/>
                                                <Button name="file_2" style={{
                                                    width: "40px",
                                                    display: "flex",
                                                    justifyContent: "center"
                                                }} onClick={this.fileDeletedHandler}>❌</Button>
                                            </InputGroup>
                                            <InputGroup className="mb-3">
                                                <FormControl type="file"
                                                             name="file_3"
                                                             onChange={this.fileSelectedHandler}/>
                                                <Button name="file_3" style={{
                                                    width: "40px",
                                                    display: "flex",
                                                    justifyContent: "center"
                                                }} onClick={this.fileDeletedHandler}>❌</Button>
                                            </InputGroup>
                                        </Card.Text>
                                        {/*<input type="file" onChange={this.fileSelectedHandler} style={{*/}
                                        {/*    marginTop: '1rem',*/}
                                        {/*    marginBottom: '3rem'}}/>*/}
                                        {/*<Button variant="primary" style={{*/}
                                        {/*    width: "100%",*/}
                                        {/*    backgroundColor: '#5cd0c5',*/}
                                        {/*    marginTop: '1rem',*/}
                                        {/*    marginBottom: '3rem'*/}
                                        {/*}}>Прикрепить фото</Button>*/}

                                        <ButtonGroup style={{width: "100%"}} onClick={this.handleSubmit}>
                                            <Button variant="primary" style={{
                                                marginRight: "2px",
                                                backgroundColor: '#5cd0c5'
                                            }}>Создать объявление</Button>

                                            <Button variant="primary" style={{
                                                marginLeft: "2px",
                                                backgroundColor: '#5cd0c5'
                                            }} href="/post/all/all">Выйти</Button>
                                        </ButtonGroup>
                                    </div>
                                </Card.Body>
                            </Col>
                        </Row>
                    </Card>
                }
            </div>
        )
    }
}