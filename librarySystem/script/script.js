let counter = 0;

function myClientSearch(event) {
    event.preventDefault();
    
    let myList = document.getElementById("searchForm");
    let output = "";

    // write inputs into table
    for (let j = 0; j < 4; j++){
        let currentId = "tr" + counter + "c" + j;
        if (j == 3){
            document.getElementById(currentId).innerHTML = (1 == Math.floor(Math.random()*2)) ? "Yes" : "No";
        }
        else{
            document.getElementById(currentId).innerHTML = myList.elements[j].value;
        }
    }
    
    // increment global counter
    counter++;
    counter%=5;

    // display inputs in log
    for (let i = 0; i < myList.length -1; i++) {
        output += myList.elements[i].value + "\n";
        myList.elements[i].value = ""; // empties the search fields
    }
    console.log(output);
    //alert("You searched for\n" + output);
    //document.getElementById("demo").innerHTML = text;
}