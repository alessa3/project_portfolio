"use client";
import React from 'react';
import stylesForm from "../../styles/Formular.module.css";
import stylesLayout from "../../styles/Layout.module.css";
import { useState } from "react";
import {set, useForm} from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";
import HeaderWithLogo from "../components/HeaderWithLogo";
import toast, {Toaster} from "react-hot-toast";

export default function FormPage () {

    // defines the accepted format for each input field of the form (name, email and the comment)
    const SignupScheme = yup.object().shape({
        userName: yup.string().required("Erzähl uns bitte, wie wir dich nennen sollen"),
        eMail: yup.string().required("Es muss eine gültige E-Mail sein =)")
            .email("Es muss eine gültige E-Mail sein =)"),
        userComment: yup.string().required("Bitte erzähl uns im Kommentarfeld, was du wissen möchtest")
            .max(300, "Kommentare können nicht länger als 300 Buchstaben sein")
    });

    // form for user input, that uses a resolver to control the input fields, can also handle submits and input
    function Form() {
        const {register,
            handleSubmit,
            formState: {errors}
        } = useForm({resolver: yupResolver(SignupScheme)});

        // requests to add values to a database through the api
        async function makeApiRequest(information:string) {

            // if empty information is given, do nothing
            try {
                if(information == ""){
                    return;
                }
                const jsonUserObject = JSON.parse(information);
                let currentWindowAsString = window.location.href;
                currentWindowAsString = currentWindowAsString.substring(0, currentWindowAsString.length - 8);
                // uses created input to fetch data from api
                const response = await fetch(`${currentWindowAsString}api/create-user-contribution?userName=${jsonUserObject.userName}
                                                                &eMail=${jsonUserObject.eMail}&userComment=${jsonUserObject.userComment}`);
                // for console purposes
                if (!response.ok) {
                    throw new Error(`API request failed with status ${response.status}`);
                }
            } catch (error) {
               throw new Error();
            }
        }

        function correctInputResponse(userInformation){
            const userName = userInformation.userName;
            // clears input content for user
            document.forms['formInput'].reset();
            // pop up message, after receiving a correct input, acts depending on success of api request
            toast.promise(makeApiRequest(JSON.stringify(userInformation)),{
                loading: 'Verarbeite deine Nachricht',
                success: <b>Hey, wir haben deine Nachricht erhalten {userName}</b> ,
                error: <b>Hey, diese Nachricht kam nicht bei uns an oder du hast sie schonmal geschickt!</b>,
            } ,{
                style: {
                    padding: '16px',
                },
                success: {
                    iconTheme: {
                        primary: '#20D7C4',
                        secondary: '#FFFFFF',
                    }
                },
                error: {
                    iconTheme: {
                        primary: '#DD6E67',
                        secondary: '#FFFFFF',
                    }
                }
            } );
            // resets content for next input
            userInformation.firstName = "";
            userInformation.eMail = "";
            userInformation.userComment = "";
        }


        return (
            <div className={stylesForm.centered}>
                <div className={stylesForm.greyBox}>
                    <h1 className={stylesForm.h1}>
                        Kontaktformular
                    </h1>
                    <div>
                        <form id= 'formInput' onSubmit={handleSubmit((data) => correctInputResponse(data))}>
                            <div id={stylesForm.inputAndButtonContainer}>
                                <div id={stylesForm.inputContainer}>
                                    <input id= "inputName" {...register("userName")} placeholder="Dein Name"  className={stylesForm.inputStyle}/>
                                        {errors.userName && <p className={stylesForm.errorMessage}>{errors.userName.message}</p>}

                                    <input id= "inputEmail" {...register("eMail")} placeholder="Deine E-mail" className={stylesForm.inputStyle}/>
                                        {errors.eMail && <p className={stylesForm.errorMessage}>{errors.eMail.message}</p>}

                                    <textarea id= "inputComment" {...register("userComment")} placeholder="Dein Comment an uns" className={stylesForm.inputStyleComment}/>
                                        {errors.userComment && <p className={stylesForm.errorMessage}>{errors.userComment.message}</p>}
                                </div>
                                <div id={stylesForm.buttonContainer}>
                                    <input id={stylesForm.submitButton} type="submit" value={"Absenden"}/>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        );
    }

    function ButtonToMainPage() {
        return (
            <div className={stylesLayout.buttonToOtherPageBox} id={stylesForm.buttonToMainPageBox}>
                <a href="/" style={{textDecoration:"none"}} >
                    <button className={stylesLayout.buttonToOtherPage} id={stylesForm.buttonToMainPage}>
                        ZUR STARTSEITE
                    </button>
                </a>
            </div>

        )
    }

    return (
        <main>
            <HeaderWithLogo></HeaderWithLogo>
            <Form></Form>
            <Toaster
                position="top-center"
                reverseOrder={false}
                containerStyle={{fontFamily: 'Viga', fontSize: '1.5em'}}/>
            <ButtonToMainPage></ButtonToMainPage>
        </main>
    )
}