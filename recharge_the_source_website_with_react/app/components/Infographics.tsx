import React, {useRef, useState} from 'react';
import Image from "next/image";
import oven from "../../public/Oven.svg";
import workingOven from "../../public/WorkingOven.gif";
import battery from "../../public/CircuitBoard.svg";
import brokenPhone from "../../public/SmartphoneWithBrokenDisplay.svg";
import insideOfPhoneWithAll from "../../public/SmartphoneWithBattery.svg";
import insideOfPhoneOnlyCircuitBoard from "../../public/SmartphoneWithCircuitBoard.svg";
import drivingTruck from "../../public/TruckDrivingAway.gif";
import metalsAsResult from "../../public/MetalsAsCoins.svg";
import {useDrag, useDrop} from "react-dnd";
import styles from "../../styles/MainPage.module.css";


function Infographics() {

    // text function, that allows variable text
    const [variableText, setVariableText] = useState("Hey! Lass uns ein Smartphone recyclen! Klicke auf das Handy, um zu starten.");

    // timeout id, that allows resetting the infographics properly for the used oven gif
    const [timeoutID, setTimeoutID] = useState(undefined);

    // allows drag and drop on the 2 images (circuit board and oven, when they appear)
    // circuit board = draggable
    const [, drag] = useDrag(() => ({
        type: "image",
        item: { id: "circuitBoard" },
        collect: (monitor) => ({
            isDragging: !!monitor.isDragging(),
        }),
    }));

    // oven = place where circuit board should be dropped
    const [, drop] = useDrop(() => ({
        accept: "image",
        drop: () =>  StartOven(),
        collect: (monitor) => ({
            isOver: !!monitor.isOver(),
        }),
    }));

    // function that switches to oven animation and lets the other parts disappear
    const StartOven = () => {
        document.getElementById("oven").setAttribute("src", workingOven.src);
        LetCircuitBoardDisappear();
        LetOvenDisappear();
    };

    // shows the oven and circuit board
    const LetOvenAndCircuitBoardAppear = () => {
        document.getElementById("draggable").style.display = "contents";
        document.getElementById("dropPlace").style.display = "contents";
        document.getElementById("circuitBoardAndOven").style.display = "contents";
    }

    // lets circuit board disappear
    const LetCircuitBoardDisappear = () => {
       document.getElementById("draggable").style.display = "none";
    }
    // sets timeout, that defines when oven gif has to stop (3s) and switch to result picture
    const LetOvenDisappear = () =>{
        setVariableText("So, wir sind fast am Ende!")

         setTimeoutID(setTimeout(( () => {
             document.getElementById("dropPlace").style.display = "none";
             setVariableText("BING! Der Ofen ist fertig! Rechts siehst du die Metalle, die du erzeugt hast." +
                 " Kupfer, Silber, Palladium, Gold und Platin sind hierbei die Metalle, die du mit einem " +
                 "einfachen Standardrecycling durch eine Schmelze hergstellt hast. Nach der Schmelze wird " +
                 "üblicherweise noch eine Elektrolyse durchgeführt, um die einzelnen Metalle voneinander " +
                 "zu trennen. Dank dir konnten wir bis zu 98% dieser Metalle zurückgewinnen." +
                 " Falls du weitere Fragen haben solltest, schreibe uns gerne über das Kontaktformular " +
                 "weiter unten!");
             document.getElementById("wonResources").style.display = "contents";
         }),3000));
    }

    // circuit board and oven disappear and reset everything to the beginning, + truck gif is being reset
    const LetCircuitBoardAndOvenDisappear = () =>{
        clearTimeout(timeoutID);
        LetCircuitBoardDisappear();

        document.getElementById("truck").setAttribute("src", "");
        document.getElementById("circuitBoardAndOven").style.display = "none";
        document.getElementById("dropPlace").style.display = "none";
        document.getElementById("oven").setAttribute("src", oven.src);

        setTimeout(() => (document.getElementById("truck").setAttribute("src", drivingTruck.src)), 1);
    }

    // is current image reference for the first few pictures
    const displayImageRef = useRef(null);

    // depending on the current shown image it will switch to the next image of the infographics on click (on the image)
    // with each image switch, the text to the image will change as well
    const handleImageClick = () => {
        if (displayImageRef.current && displayImageRef.current.src.match(brokenPhone.src)) {
            displayImageRef.current.src = insideOfPhoneWithAll.src;
            // will make reset button visible
            document.getElementById("infographicsResetButton").style.opacity = "1";
            setVariableText("So, jetzt haben wir erstmal das Display gelöst und schauen uns das Innere vom Smartphone an. " +
                 "Das gelöste Display, der Akku und die darunterliegende Leiterplatte können recyclet werden. " +
                 "Aus dem Display kann das Metall Idium gewonnen werden." +
                 " Aus dem Akku können Metalle wie Lithium und Kobalt und auch seltene Erden zurückgewonnen werden." +
                 "Im nächsten Schritt schauen wir uns jedoch das Recycling der Leiterplatte aufgrund der hohen Metallwerte genauer an. " +
                 "Klicke dafür auf das Handy!");

        }else if(displayImageRef.current && displayImageRef.current.src.match(insideOfPhoneWithAll.src)){
            displayImageRef.current.src = insideOfPhoneOnlyCircuitBoard.src;
            setVariableText("Die Leiterplatte enthält 90% des Goldes, 98% des Kupfers," +
                " 99% des Palladiums, 86% des Indiums und 93% des Tantals, die insgesamt in einem Handy verbaut sind. " +
                "Aufgrund der vielen verschiedenen verbauten Metalle und ihrem hohen Metallwert" +
                " ist sie für den Recyclingprozess sehr attraktiv." +
                " Um die Leiterplatte zu recyclen, musst du sie aus dem Handy nehmen." +
                " Um sie rauszunehmen, klicke auf das Handy.");

        }else if(displayImageRef.current && displayImageRef.current.src.match(insideOfPhoneOnlyCircuitBoard.src)){
            document.getElementById("clickableInfographics").style.display = "none";
            setVariableText("Jetzt aber genug Infos! Lass uns die Leiterplatte recyceln!" +
                " Dafür musst du die Platte in den Ofen legen, damit wir ihre Metalle einschmelzen können!\n")
            LetOvenAndCircuitBoardAppear();
        }
    };

    // resets the infographics to the start setting, so a user can start from the beginning of the infographics again
    const handleButtonClick = () => {
        document.getElementById("infographicsResetButton").style.opacity = "0";
        document.getElementById("wonResources").style.display = "none";
        document.getElementById("clickableInfographics").style.display = "contents";

        setVariableText("Hey! Lass uns ein Smartphone recyclen! Klicke auf das Handy, um zu starten.");
        LetCircuitBoardAndOvenDisappear();


        displayImageRef.current.src = brokenPhone.src;
    };

    return (
        <>
            <div className={styles.infographicsBox}>
                <div className={styles.infographicsTextBox}>
                    <button onClick={handleButtonClick} className={styles.startSquare} id="infographicsResetButton">
                        Starte von vorne
                    </button>
                    <p className={styles.infographicsTextSettings}>
                        {variableText}
                    </p>
                </div>
                <div className={styles.infographicsInteractiveContentBox}>
                    <div>
                            <div id="clickableInfographics" className={styles.phoneImageContainer}>
                                <Image ref={displayImageRef} src= {brokenPhone.src} id= "clickPhone"
                                       alt="Kaputtes Handy" onClick={handleImageClick} className={styles.handyInfographic}
                                       width={340} height={340}>
                                </Image>
                            </div>
                    </div>
                    <div id = "circuitBoardAndOven" style={{display : "none"}}>
                        <div className={styles.ovenAndCBoardBox}>

                            <div id = "draggable"  className={styles.circuitBoard}>
                                <Image ref={drag} id="circuitBoard" src={battery} alt={"smartphone"} width={150} height={150}></Image>
                            </div>

                            <div id = "dropPlace" className={styles.oven}>
                                <Image ref={drop} id="oven" src={oven} alt={"oven"} width={300} height={300}></Image>
                            </div>

                        </div>
                        <div className={styles.truckBox}>
                            <div id ="truckBox">
                                <Image id = "truck" src={drivingTruck} alt={"Truck driving away with smartphone parts"} width={480} height={400}></Image>
                            </div>
                        </div>
                    </div>
                    <div className={styles.wonResources}>
                        <div id = "wonResources" style={{display : "none"}} >
                            <Image src={metalsAsResult} className={styles.wonResourcesBox} alt={"picture of won resources through the recycling of one circuit board"}></Image>
                        </div>
                    </div>
                </div>

            </div>
        </>
    )
}
export default Infographics;