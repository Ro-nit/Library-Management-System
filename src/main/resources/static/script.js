let currentMemberId = null;

// MEMBER LOGIN
function showOptions() {
    let memberId = document.getElementById("memberId").value;
    if (memberId == "") {
        alert("Please enter Member ID");
        return;
    }
    fetch("/members/" + memberId)
        .then(response => {
            if (!response.ok) {
                throw new Error("Member not found");
            }
            return response.json();
        })
        .then(member => {
            currentMemberId = member.memberId;
            alert("Welcome " + member.memberName);
            showMemberOptions();
        })
        .catch(error => {
            alert(error.message);
        })
}

// SHOW OPTIONS AGAIN
function showMemberOptions() {
    document.getElementById("memberOptions").style.display = "block";
    document.getElementById("bookSection").style.display = "none";
    document.getElementById("issuedSection").style.display = "none";
}

// SHOW BOOKS SECTION
function showAllBooks() {
    document.getElementById("memberOptions").style.display = "none";
    document.getElementById("bookSection").style.display = "block";
    document.getElementById("issuedSection").style.display = "none";
    getBooks();
}

// SHOW ISSUED SECTION
function showIssuedBooks() {
    document.getElementById("memberOptions").style.display = "none";
    document.getElementById("issuedSection").style.display = "block";
    document.getElementById("bookSection").style.display = "none";
    getMyIssues();
}

// SHOW ALL BOOKS
function getBooks() {
    fetch("/books")
        .then(response => {
            if (!response.ok) {
                throw new Error("Unable to fetch books");
            }
            return response.json();
        })
        .then(books => {
            let bookList = document.getElementById("bookList");
            bookList.innerHTML = "";
            for (let i = 0; i < books.length; i++) {
                let book = books[i];
                bookList.innerHTML +=
                    "<p>" +
                    "Book ID: " + book.bookId + "<br>" +
                    "Book Name: " + book.bookTitle + "<br>" +
                    "Author: " + book.bookAuthor + "<br>" +
                    "Available Copies: " + book.bookCopiesAvailable +
                    "<br>" +
                    (book.bookCopiesAvailable > 0
                        ? "<button onclick='issueBook(" + book.bookId + ")'>Issue Book</button>"
                        : "Currently unavailable") +
                    "<br><br>" +
                    "</p>";
            }
        })
}

// ISSUE BOOK
function issueBook(bookId) {
    fetch("/issues", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            memberId: currentMemberId,
            bookId: bookId
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Unable to issue book");
            }
            return response.json();
        })
        .then(issue => {
            alert("Book issued successfully!");
            showMemberOptions();
        })
}

// MEMBER'S ISSUED BOOKS
function getMyIssues() {
    fetch("/issues/member/" + currentMemberId)
        .then(response => {
            if (!response.ok) {
                throw new Error("Unable to fetch issued books");
            }
            return response.json();
        })
        .then(issues => {
            let issuedBookList = document.getElementById("issuedBookList");
            issuedBookList.innerHTML = "";
            if (issues.length == 0) {
                issuedBookList.innerHTML = "You have no books to return.";
                return;
            }
            for (let i = 0; i < issues.length; i++) {
                let issue = issues[i];
                issuedBookList.innerHTML +=
                    "<p>" +
                    "Book Name: " + issue.bookTitle + "<br>" +
                    "Issue Date: " + issue.issueDate + "<br>" +
                    "<button onclick='returnBook(" +
                    currentMemberId + ", " + issue.bookId +
                    ")'>Return Book</button>" +
                    "<br><br>" +
                    "</p>";
            }
        })
}

// RETURN BOOK
function returnBook(memberId, bookId) {
    fetch("/issues/return", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            memberId: memberId,
            bookId: bookId
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Unable to return book");
            }
            return response.json();
        })
        .then(issue => {
            alert("Book returned successfully!");
            getMyIssues();
            showMemberOptions();
        })
}

// FULL ISSUE HISTORY
function showEveryIssue(){

    document.getElementById("loginPage").style.display = "none";
    document.getElementById("memberOptions").style.display = "none";
    document.getElementById("bookSection").style.display = "none";
    document.getElementById("issuedSection").style.display = "none";
    document.getElementById("historySection").style.display = "block";

    fetch ("/issues")
        .then(response => {
            if(!response.ok){
                throw new Error("no issues");
            }
            return response.json();
        })
        .then(issues =>{
            let issueList = document.getElementById("issueList");
            issueList.innerHTML = "";
            for(let i=0; i<issues.length; i++){
                let issue = issues[i];
                issueList.innerHTML +=
                    "<p>" +
                    "Issue ID: " + issue.issueId + "<br>" +
                    "Book Name: " + issue.bookTitle + "<br>" +
                    "Member Name: " + issue.memberName + "<br>" +
                    "Issue Date: " + issue.issueDate + "<br>" +
                    "Return Date: " + (issue.returnDate == null ? "Not Returned" : issue.returnDate) +
                    "<br><br>" +
                    "</p>";
            }
        })
}
