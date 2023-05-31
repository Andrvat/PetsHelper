import React, {Component} from 'react'
import {Carousel} from "react-bootstrap";
import img_1 from "../../../images/post_cat_1.jpg"
import img_2 from "../../../images/post_cat_2.jpg"
import img_3 from "../../../images/post_cat_3.jpg"
import "./post.css"

export default class PostImgCarousel extends Component {
    render() {
        return (
            <Carousel variant="top">
                <Carousel.Item>
                    <img
                        className="carousel-img"
                        src={img_1}
                    />
                </Carousel.Item>
                <Carousel.Item>
                    <img
                        className="carousel-img"
                        src={img_2}
                    />
                </Carousel.Item>
                <Carousel.Item>
                    <img
                        className="carousel-img"
                        src={img_3}
                    />
                </Carousel.Item>
            </Carousel>
        )
    }
}