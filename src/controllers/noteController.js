const noteModel = require('../models/note');

const createNote = async (req, res) => {
    // console.log("req user id -> ", req.userId);

    const { title, description } = req.body;

    const newNote = new noteModel({
        title: title,
        description: description,
        userId: req.userId
    });

    try {
        await newNote.save();
        res.status(201).json(newNote);
    } catch (error) {
        console.log("Create Note Error -> ", error.message);
        res.status(500).json({ message: error.message });
    }
}

const updateNote = async (req, res) => {
    const id = req.params.id;
    const { title, description } = req.body;

    try {
        // 1️⃣ Check if note exists
        const note = await noteModel.findById(id);
        if (!note) {
            return res.status(404).json({ message: "Note not found" });
        }

        // 2️⃣ Check if the note belongs to the logged-in user
        if (note.userId.toString() !== req.userId) {
            return res.status(403).json({ message: "Unauthorized: You can't edit this note" });
        }

        // 3️⃣ Update the note
        note.title = title;
        note.description = description;

        const updatedNote = await note.save();

        res.status(200).json(updatedNote);

    } catch (error) {
        console.log(error);
        res.status(500).json({ message: "Something went wrong" });
    }
};


const deleteNote = async (req, res) => {
    const id = req.params.id;

    try {
        // 1️⃣ Check if note exists
        const note = await noteModel.findById(id);
        if (!note) {
            return res.status(404).json({ message: "Note not found" });
        }

        // 2️⃣ Check if note belongs to logged-in user
        if (note.userId.toString() !== req.userId) {
            return res.status(403).json({ message: "Unauthorized: You cannot delete this note" });
        }

        // 3️⃣ Delete the note
        await noteModel.findByIdAndDelete(id);

        res.status(200).json({ message: "Note deleted successfully" });

    } catch (error) {
        console.log("Delete Note Error -> ", error.message);
        res.status(500).json({ message: "Something went wrong" });
    }
};


const getNote = async (req, res) => {

    try {
        const notes = await noteModel.find({ userId: req.userId });
        res.status(200).json(notes);
    }
    catch (error) {
        console.log("Get Note Error -> ", error.message);
        res.status(500).json({ message: error.message });
    }

}

module.exports = {
    createNote,
    updateNote,
    deleteNote,
    getNote
}