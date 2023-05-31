import React, { useEffect, useState } from "react";
import {
  Card,
  Container,
  ListGroup,
  ListGroupItem,
  Row,
} from "react-bootstrap";
import { Link, useParams, useSearchParams } from "react-router-dom";
import { withRouter } from "../../utilites/withRouter";
import {ACTUAL_BACKEND_ADDRESS} from "../../AppConstants";

// class PostGrid extends Component {
const PostGrid = () => {
  const defaultPostsList = [
    {
      postId: 0,
      postHeader: "Возьму собаку",
      description:
        "Возьму щенка корги. У самого 2 кошки, но думаю, они поладят с новым другом. Опыта воспитания собак нет. О цене договоримся. Ваш щенок будет в надежных руках, активный образ жизни, прогулки и здоровое питание ему будут обеспечены.",
      date: "2022-11-16 14:29:07",
      authorUsername: "danillo19",
      city: "Абан",
      section: "bulletin-board",
      category: "take",
      tags: "#qqq",
    },

    {
      postId: 1,
      postHeader: "Помогите пристроить!!!",
      description:
        "Две кошки и кот. Кошки молоденькие, нестерелизованные. Кот кострированный. У самих две собаки и кот. У нас дом стоит на продаже,в любое время можем уехать и кошки останутся никому ненужными.",
      date: "2022-02-20 02:55:43",
      authorUsername: "ankifox",
      city: "Ишим",
      section: "bulletin-board",
      category: "give",
      tags: "#www",
    },
  ];
  const [state, setState] = useState({
    postsList: [],
  });
  const { section, category } = useParams();
  const [searchParams] = useSearchParams();

  useEffect(() => {
    const getData = async () => {
      const tags = searchParams.get("tags");
        console.log("tags: ", tags, !!tags);
        console.log("category: ", category);
        console.log("section: ", section);

      //   const postsList = defaultPostsList.filter(
      //     (x) =>
      //       (section === "all" || x.section === section) &&
      //       (category === "all" || x.category === category) &&
      //       (!tags || x.tags.includes(tags))
      //   );
      //   setState({
      //     postsList: postsList,
      //   });

      let url = !!tags
        ? ACTUAL_BACKEND_ADDRESS + `post/tags/${tags.replace('#', '%23')}`
        : section === "all"
        ? ACTUAL_BACKEND_ADDRESS + "post/all"
        : ACTUAL_BACKEND_ADDRESS + `post/category/${category}`;

      const response = await fetch(url, {
        method: "GET", // *GET, POST, PUT, DELETE, etc.
        mode: "cors", // no-cors, cors, *same-origin
        // cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
        // credentials: "same-origin", // include, *same-origin, omit
        headers: {
          "Content-Type": "application/json",
          // "Content-Type": "application/x-www-form-urlencoded",
        },
        redirect: "follow", // manual, *follow, error
        referrer: "no-referrer", // no-referrer, *client
      });

      if (!response.ok) throw new Error(response.status);

      const data = await response.json();

      setState({ postsList: data });
    };

    getData();
  }, [category, searchParams, section]);

  return (
    <div className="App">
      <Container className="p-4">
        {state.postsList.length === 0 ? (
          <Card
            style={{
              width: "100",
              height: "4rem",
              marginTop: "5rem",
              justifyContent: "center",
              color: "#626262",
              fontWeight: "bold",
              fontFamily: "sans-serif",
            }}
          >
            По вашему запросу ничего не найдено.
          </Card>
        ) : (
          <Row>
            {state.postsList.map((post) => (
              <Card
                key={post.id}
                style={{
                  width: "18rem",
                  marginTop: "5rem",
                  marginInline: "15px",
                }}
              >
                <Card.Img
                  variant="top"
                  src={"/images/Beautiful-bird.jpg"}
                  style={{ marginTop: "11px" }}
                />
                <Card.Body>
                  <Link
                    to={"/post/id/" + post.id}
                    style={{ textDecoration: "none" }}
                  >
                    <Card.Title
                      style={{ color: "#383838", fontStyle: "italic" }}
                    >
                      {post.postHeader}
                    </Card.Title>
                  </Link>
                  <Card.Text style={{ textAlign: "justify" }}>
                    {post.description}
                  </Card.Text>
                </Card.Body>
                <ListGroup className="list-group-flush">
                  <Link
                    to={"/user/" + post.authorUsername}
                    style={{ textDecoration: "none" }}
                  >
                    <ListGroupItem
                      href={"/user/" + post.authorUsername}
                      style={{ color: "#383838", fontWeight: "bold" }}
                    >
                      {"Автор: @" + post.authorUsername}
                    </ListGroupItem>
                  </Link>
                  <ListGroupItem>{"Город: " + post.city}</ListGroupItem>
                  <ListGroupItem>{"От: " + post.date}</ListGroupItem>
                </ListGroup>
              </Card>
            ))}
          </Row>
        )}
      </Container>
    </div>
  );
};

export default withRouter(PostGrid);
