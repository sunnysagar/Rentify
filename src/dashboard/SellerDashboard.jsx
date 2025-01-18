import React, { useEffect, useRef, useState } from "react";
import { useAuth } from "../context/authService";
import axios from "axios";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import "../cssPages/SellerDashboard.css";
import { useNavigate } from "react-router-dom";

const SellerDashboard = () =>{

    const[properties, setProperties] = useState([]);
    const { authToken } = useAuth();
    const stompClient = useRef(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchSellerProperties = async() => {
            try {
                const response = await axios.get('http://localhost:8010/rentify/property/seller', {
                    headers: { Authorization: `Bearer ${authToken}`},
                });
                setProperties(response.data);
            } catch (error) {
                console.error('Error fetching your properties: ', error);
            }
        };

        const connect = () => {
            const socket = new SockJS('http://localhost:8010/websocket');
            const client = new Client({
                webSocketFactory: () => socket,
                debug: (str) => {console.log(str);},
                reconnectDelay: 5000,
                connectHeaders: {
                    Authorization: `Bearer ${authToken}`,
                },
                onConnect: (frame) => {
                    console.log('connected: ' + frame);
                    client.subscribe('/topic/properties', onPropertyUpdate);
                },
                onStompError: (error) => {
                    console.error('STOMP error', error);
                }
            });

            // setting the client in the ref
            stompClient.current = client;
            client.activate();
        };

        const onPropertyUpdate = (message) => {
            console.log('Received message: ', message.body);
            const updatedProperty = JSON.parse(message.body).data;

            setProperties((prevProperties) => {
                // update teh specific property or add it if it doesn't exit
                const propertyIndex = prevProperties.findIndex(p => p.id === updatedProperty.id);
                if(propertyIndex !== -1){
                    // update the existing property
                    const updatedProperties = [...prevProperties];
                    updatedProperties[propertyIndex] = updatedProperty;
                    return updatedProperties;
                } else{
                    // add new property
                    return [...prevProperties, updatedProperty];
                }
            });
        };

        fetchSellerProperties();
        connect();

        return () =>{
            if(stompClient.current){
                stompClient.current.deactivate();
            }
        };
    }, [authToken]);


    const openUpdatePage = (property) => {
        navigate(`/update-property/${property.id}`, { state: { property } });
    }
    return(
        <div className="seller-dashboard">
            <h1>Your Properties</h1>
            <div className="property-list">
                {properties.length > 0 ? (
                    properties.map((property) => (
                        <div key={property.id} className="property-card">
                            {property.image && <img src={property.image} alt={property.title} /> }

                            <div className="property-card-content">
                                <h2>{property.title}</h2>
                                <p>{property.description}</p>

                                <div className="property-details">
                                    <div className="property-detail">
                                        <strong>Address:</strong> <p>{property.address}</p>
                                    </div>
                                    <div className="property-detail">
                                        <strong>Bedrooms:</strong> <p>{property.bedrooms}</p>
                                    </div>
                                    <div className="property-detail">
                                        <strong>Bathrooms:</strong> <p>{property.bathrooms}</p>
                                    </div>
                                    <div className="property-detail">
                                        <strong>Area:</strong> <p>{property.area}</p>
                                    </div>
                                    <div className="property-detail">
                                        <strong>Price:</strong> <p>{property.price}</p>
                                    </div>
                                    <div className="property-detail">
                                        <strong>Nearby Amenities:</strong>{" "}
                                        <p>{property.nearbyAmenities}</p>
                                    </div>
                                    <div className="property-detail">
                                        <strong>Property Type:</strong>{" "}
                                        <p>{property.propertyType}</p>
                                    </div>
                                 </div>

                            </div>
                            <div className="curd-btn">
                                <button onClick={() => openUpdatePage(property)}>Update</button>
                                <button>Delete</button>
                            </div>
                           
                        </div>
                    ))
                ): (
                    <p>Loading...</p>
                )}
            </div>
        </div>
    );

}

export default SellerDashboard;