//
//  ContentView.swift
//  QuizApp
//
//  Created by Teddy Taussig on 4/30/26.
//

import SwiftUI

struct ContentView: View {
    
    @State private var resultText = ""
    @State private var resultColor: Color = .clear
    
    let question = "Who is the CEO of Apple?"
    
    let answers: [(text: String, isCorrect: Bool)] = [
        ("a. Bill Gates", false),
        ("b. Steve Jobs", false),
        ("c. Tim Cook", true),
        ("d. Larry Holder", false)
    ]
    
    var body: some View {
        VStack {
            
            Text("Quiz App")
                .font(.largeTitle)
                .fontWeight(.bold)
                .padding(.top, 80)
            
            Text(question)
                .font(.title2)
                .fontWeight(.semibold)
                .multilineTextAlignment(.center)
                .padding(.top, 40)
                .padding(.horizontal)
            
            VStack(spacing: 22) {
                ForEach(answers, id: \.text) { answer in
                    Button {
                        checkAnswer(answer)
                    } label: {
                        Text(answer.text)
                            .font(.title3)
                            .fontWeight(.medium)
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .padding()
                            .background(Color.blue.opacity(0.12))
                            .foregroundColor(.blue)
                            .cornerRadius(12)
                    }
                    .buttonStyle(.plain)
                }
            }
            .frame(width: 260)
            .padding(.top, 45)
            
            Text(resultText)
                .font(.title)
                .fontWeight(.bold)
                .foregroundColor(resultColor)
                .padding(.top, 40)
                .opacity(resultText.isEmpty ? 0 : 1)
            
            Spacer()
        }
        .frame(maxWidth: .infinity)
        .background(Color.white)
    }
    
    func checkAnswer(_ answer: (text: String, isCorrect: Bool)) {
        if answer.isCorrect {
            resultText = "Correct"
            resultColor = .green
        } else {
            resultText = "Incorrect"
            resultColor = .red
        }
    }
}

#Preview {
    ContentView()
}
