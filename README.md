# 📦 Packer Unpacker (Java GUI)

## 📖 Overview

Packer Unpacker is a Java-based desktop application built using Swing that allows users to pack multiple files into a single file and unpack them when required.

This project demonstrates file handling, byte-level processing, and GUI-based interaction by implementing the complete logic from scratch without using external libraries.

---

## 🎯 Objective

* To implement file packing and unpacking manually
* To understand file handling and byte-level operations
* To develop an interactive GUI using Java Swing

---

## Key Concepts

* Java Swing for GUI development
* File handling using FileInputStream and FileOutputStream
* Byte manipulation
* XOR-based encryption technique
* Event handling using ActionListener

---

## Features

* Pack multiple files from a folder into a single file
* Unpack files from a packed file
* Stores file metadata such as file name and size
* Uses XOR operation for reversible encryption
* GUI-based interaction with input fields and buttons
* Displays success and error messages using dialog boxes

---

## Working Principle

### Packing

* Reads all files from the specified folder
* Creates a header containing file name and file size
* Applies XOR operation on file data
* Writes header and encrypted data into a single packed file

### Unpacking

* Reads packed file sequentially
* Extracts header information
* Applies XOR operation to decrypt data
* Recreates original files

---

## ▶️ How to Run

Step 1: Compile the program

```bash
javac PackerUnpackerGUI.java
```

Step 2: Run the application

```bash
java PackerUnpackerGUI
```

---

## How to Use

After running the program:

* A GUI window will appear
* Select either Pack or Unpack

For Packing:

* Enter folder name containing files
* Enter output packed file name
* Click Execute

For Unpacking:

* Enter packed file name
* Click Execute

---

## Project Structure

* PackerUnpackerGUI.java : Contains GUI, packing logic, and unpacking logic

---

## Design Highlights

* Complete implementation without using external libraries
* Combines GUI with low-level file operations
* Implements custom file packing logic
* Uses XOR for simple encryption and decryption

---

## Note

* This project is designed for educational purposes
* Works on local file system
* XOR encryption is basic and not suitable for production security

---

## 👨‍💻 Author

Varad Muley
---
