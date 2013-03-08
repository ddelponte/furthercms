// Removes hyphens (but not spaces) on the first replace, and in the second replace it will condense consecutive spaces into a single hyphen.
// So "like - this" comes out as "like-this"
function convertToSlug(text) {
    return text
            .toLowerCase()
            .replace(/[^\w ]+/g, '')
            .replace(/ +/g, '-');
}