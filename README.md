//this text was Gnerated by AI
Prompt used: "Create a detailed and beginner-friendly description for a GitHub repository based on the Java project sent to you. The code reads a CSV file of word and suffix mappings and encodes sentences into lists of numbers based on direct word matches or root + suffix combinations. Make it informative, structured, and suitable for showcasing the project’s purpose, features, and use cases."

🔐 Word Encoder & Suffix-Based Compression Tool
This Java project reads a CSV-based dictionary of full words and suffixes, then encodes sentences into compressed numerical representations. Ideal for lightweight NLP tasks, indexing, or custom language modeling.

🚀 Features
Parses a CSV file containing word → number mappings

Handles full-word and root + suffix combinations (e.g., "unhappiness" → [140, 55])

Encodes entire sentences into number sequences

Includes cleaning for punctuation and case sensitivity

Modular functions: encodeWord, encodeLine, and a pluggable loadEncodingMaps

Built for as a final project for Data structure and Algorithms module.
