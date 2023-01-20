function confirmation(e) {
    if (confirm('Do you want to submit?')) {
    } else {
        e.preventDefault();
    }
}