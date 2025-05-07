i made this out of boredom 

# File Encryption Tool

A simple Java-based tool for encrypting and decrypting files using AES encryption.

## Features
- Encrypt files with AES algorithm
- Decrypt files using a generated key
- Simple folder-based workflow

## Prerequisites
- Java Runtime Environment (JRE) 8 or later
- Basic understanding of file encryption concepts

## Installation
1. Clone or download this repository
2. Ensure you have Java installed

## Usage

### Encryption
1. Create two folders in the `src` directory:
   - `key` (for storing encryption keys)
   - `files` (for files to encrypt/decrypt)
2. Place files you want to encrypt in the `files` folder
3. Run `vvv1.java`

The tool will:
- Generate an encryption key (saved in `key/thekey.key`)
- Encrypt all files in the `files` folder
- Create encrypted versions with `.enc` extension

### Decryption
1. Move `thekey.key` from the `key` folder to the `files` folder
2. Place encrypted files (`.enc` extension) in the `files` folder
3. Run `rev_vvv1.java`

The tool will:
- Use the key to decrypt the files
- Save decrypted versions without the `.enc` extension

## One-Click Execution

### Windows Users:
1. Double-click:
   - `encrypt.bat` to encrypt files
   - `decrypt.bat` to decrypt files

### macOS/Linux Users:
I am bored so another time maybe

## Security Notes
- Always keep your `thekey.key` file secure - anyone with this file can decrypt your files
- The original key will remain in the `key` folder after encryption - consider deleting it if not needed
- For maximum security, store keys separately from encrypted files

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you'd like to change.

## Disclaimer
This tool is provided as-is. The author is not responsible for any data loss or security breaches resulting from its use.
