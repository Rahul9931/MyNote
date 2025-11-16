const userModel = require("../models/user")
const bcrypt = require("bcrypt")
const jwt = require("jsonwebtoken")
const SECRET_KEY = process.env.SECRET_KEY
const signup = async(req, res) => {
    // Existing User check
    // Hashed Password
    // User Creation
    // Token Generate
    const {username, email, password} = req.body;
    try{
        const existingUser = await userModel.findOne({email : email});
        if(existingUser){
            return res.status(400).json({message: "User already exists"});
        }
        
        const hashedPassword = await bcrypt.hash(password, 10)

        const result = await userModel.create({
            email: email,
            password: hashedPassword,
            username: username
        })

        const token = jwt.sign({email : result.email, id : result._id}, SECRET_KEY)

        res.status(201).json({user: result, token: token})

    }
    catch(error){
        console.log("signup error -> ", error.message)
        res.status(500).json({message: `signup error -> ${error.message}`})
    }
}

const signin = async(req, res) => {
    const {email, password} = req.body;

    try{
        const existingUser = await userModel.findOne({email : email});
        if(!existingUser){
            return res.status(400).json({message: "User not found"});
        }

        const matchPassword = await bcrypt.compare(password, existingUser.password);

        if(!matchPassword){
            return res.status(400).json({message : "Invalid Credentials"});
        }

        const token = jwt.sign({email : existingUser.email, id : existingUser._id}, SECRET_KEY);
        res.status(200).json({user: existingUser, token: token});
    }
    catch(e){
        console.log("signin error -> ", error.message)
        res.status(500).json({message: `signin error -> ${error.message}`})
    }
}

module.exports = { signup, signin }