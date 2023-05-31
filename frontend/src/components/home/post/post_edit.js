import {Component} from "react";
import {
    Button,
    ButtonGroup,
    Card,
    Col,
    Container,
    FormControl,
    FormSelect,
    InputGroup,
    Nav,
    Row
} from "react-bootstrap";
import cardImg from "../../../images/user_photo.jpg";
import React from "react";
import {withRouter} from "../../../utilites/withRouter";
import {Link} from "react-router-dom";
import {Navigate} from "react-router";
import {ACTUAL_BACKEND_ADDRESS} from "../../../AppConstants";

class PostEditor extends Component {

    state = {
        postId: "",
        postHeader: "",
        description: "",
        actuality: "",
        date: "",
        sectionName: "Объявления",
        categoryName: "Отдам в добрые руки",
        city: "",
        tags: "",
        isEdited: false,
    }


    constructor(props) {
        super(props);
        this.state.postId = props.params.postId;
        console.log("ID: " + this.state.postId)
        this.changeSelect = this.changeSelect.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
    }

    componentDidMount() {
        this.getData();
    }

    async getData() {
        const response = await fetch(ACTUAL_BACKEND_ADDRESS + "post/" + this.state.postId, {
                method: "GET",
                mode: "cors",
                headers: {
                    "Content-Type": "application/json",
                },
                redirect: "follow",
                referrer: "no-referrer",
            }
        );

        if (!response.ok) throw new Error(response.status);

        const data = await response.json();

        console.log("DATA:")
        console.log(data)

        this.setState({postHeader: data.postHeader});
        this.setState({actuality: data.actuality});
        this.setState({description: data.description});
        this.setState({sectionName: data.sectionName});
        this.setState({categoryName: data.categoryName});
        this.setState({city: data.city});
        this.setState({date: data.date});
        this.setState({tags: data.tags});
    }

    changeSelect(event) {
        const target = event.target;
        const name = target.name;
        this.setState({[name]: target.value});

        if (name === "section") {
            this.setState({
                categoryName:
                    target.value === "Объявления" ? "Отдам в добрые руки"
                        : target.value === "Мероприятия" ? "Конкурсы"
                            : target.value === "Вопросы-ответы" ? "Ветеринария"
                                : target.value === "Организации" ? "Благотворительные фонды" : null
            });
        }
        console.log(this.state)
    }

    async handleSubmit() {
        console.log("STATE:")
        console.log("ID: " + this.state.postId)
        const response = await fetch(ACTUAL_BACKEND_ADDRESS + "post/" + this.state.postId + "/edit", {
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

        if (!response.ok) {
            throw Error(response.status)
        }

        const data = await response.json();
        this.setState({[this.state.id]: data})
        console.log(data);

        this.setState({isEdited : true});
    }

    render() {
        let isEdited = this.state.isEdited;
        console.log(this.state.sectionName)
        console.log(this.state.categoryName)
        console.log("STATE:")
        console.log(this.state)
        let section = this.state.sectionName;
        let category = this.state.categoryName;
        return (
            <div>
                {isEdited && <Navigate to={"/post/id/" + this.state.postId} replace={true} />}
                <Card style={{width: 'auto', marginTop: '7rem'}}>
                    <Row className='no-gutters'>

                        <Col style={{margin: '20px 20px 20px 11px'}}>
                            <Card.Body>
                                <div className='mx-4'>
                                    <Card.Subtitle style={{marginTop: '20px', marginBottom: "6px"}}>Заголовок
                                        объявления:</Card.Subtitle>
                                    <Card.Text>
                                        <input className="mb-3 w-100" name="postHeader" maxLength="40" style={{
                                            height: "2rem",
                                            borderColor: "#cccccc",
                                            borderTopColor: "#ffffff",
                                            borderLeftColor: "#ffffff",
                                            borderRadius: "4px",
                                            borderWidth: "1px"
                                        }} value={this.state.postHeader} onChange={this.changeSelect}>
                                        </input>
                                    </Card.Text>

                                    <Card.Subtitle
                                        style={{marginTop: '20px', marginBottom: "6px"}}>Описание:</Card.Subtitle>
                                    <Card.Text>
                                        <InputGroup className="mb-3">
                                            <FormControl as="textarea" name="description" onChange={this.changeSelect}
                                                         value={this.state.description}/>
                                        </InputGroup>
                                    </Card.Text>

                                    <Card.Subtitle
                                        style={{marginTop: '20px', marginBottom: "6px"}}>Секция:</Card.Subtitle>
                                    <Card.Text>
                                        <InputGroup list="browsers" name="browser" id="browser" className="mb-3">
                                            <FormSelect name="sectionName" value={this.state.sectionName}
                                                        onChange={this.changeSelect}>
                                                <option>Объявления</option>
                                                <option>Мероприятия</option>
                                                <option>Вопросы-ответы</option>
                                                <option>Организации</option>
                                            </FormSelect>
                                        </InputGroup>
                                    </Card.Text>

                                    {section === "Объявления"
                                        ? <div>
                                            <Card.Subtitle
                                                style={{
                                                    marginTop: '20px',
                                                    marginBottom: "6px"
                                                }}>Категория:</Card.Subtitle>
                                            <Card.Text>
                                                <InputGroup className="mb-3">
                                                    <FormSelect name="categoryName" value={this.state.categoryName}
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
                                        : section === "Мероприятия"
                                            ? <div>
                                                <Card.Subtitle style={{
                                                    marginTop: '20px',
                                                    marginBottom: "6px"
                                                }}>Категория:</Card.Subtitle>
                                                <Card.Text>
                                                    <InputGroup list="browsers" name="browser" id="browser"
                                                                className="mb-3">
                                                        <FormSelect name="categoryName" value={this.state.categoryName}
                                                                    onChange={this.changeSelect}>
                                                            <option>Конкурсы</option>
                                                            <option>Выставки</option>
                                                        </FormSelect>
                                                    </InputGroup>
                                                </Card.Text>
                                            </div>
                                            : section === "Вопросы-ответы"
                                                ? <div>
                                                    <Card.Subtitle style={{
                                                        marginTop: '20px',
                                                        marginBottom: "6px"
                                                    }}>Категория:</Card.Subtitle>
                                                    <Card.Text>
                                                        <InputGroup list="browsers" name="browser" id="browser"
                                                                    className="mb-3">
                                                            <FormSelect name="categoryName" value={this.state.categoryName}
                                                                        onChange={this.changeSelect}>
                                                                <option>Ветеринария</option>
                                                                <option>Содержание</option>
                                                                <option>Поведение и воспитание</option>
                                                                <option>Продолжение рода</option>
                                                            </FormSelect>
                                                        </InputGroup>
                                                    </Card.Text>
                                                </div>
                                                : section === "Организации"
                                                    ? <div>
                                                        <Card.Subtitle style={{
                                                            marginTop: '20px',
                                                            marginBottom: "6px"
                                                        }}>Категория:</Card.Subtitle>
                                                        <Card.Text>
                                                            <InputGroup list="browsers" name="browser" id="browser"
                                                                        className="mb-3">
                                                                <FormSelect name="categoryName" value={this.state.categoryName}
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
                                            <FormControl placeholder={this.state.tags} onChange={this.changeSelect}
                                                         name="tags"/>
                                        </InputGroup>
                                    </Card.Text>

                                    <Card.Subtitle
                                        style={{marginTop: '20px', marginBottom: "6px"}}>Мультимедиа:</Card.Subtitle>
                                    <Card.Body className="mx-2">
                                        <Card.Text>Img_1.jpg ❌</Card.Text>
                                        <Card.Text>Img_2.jpg ❌</Card.Text>
                                        <Card.Text>Img_3.jpg ❌</Card.Text>
                                    </Card.Body>

                                    <Button variant="primary" style={{
                                        width: "100%",
                                        backgroundColor: '#5cd0c5',
                                        marginTop: '1rem',
                                        marginBottom: '3rem'
                                    }}>Прикрепить фото</Button>

                                    <ButtonGroup style={{width: "100%"}}>
                                        {/*<Link to={"/post/id/" + this.state.postId}>*/}
                                        <Button variant="primary" style={{
                                            marginRight: "2px",
                                            backgroundColor: '#5cd0c5'
                                        }} onClick={this.handleSubmit}
                                        >Сохранить изменения</Button>
                                        {/*</Link>*/}
                                        <Button variant="primary" style={{
                                            marginLeft: "2px",
                                            backgroundColor: '#5cd0c5'
                                        }}>Отменить и выйти</Button>
                                    </ButtonGroup>
                                </div>
                            </Card.Body>
                        </Col>
                    </Row>
                </Card>
            </div>
        )
    }
}

export default withRouter(PostEditor);