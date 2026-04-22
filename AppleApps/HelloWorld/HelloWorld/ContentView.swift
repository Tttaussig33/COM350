//
//  ContentView.swift
//  HelloWorld
//
//  Created by Teddy Taussig on 4/21/26.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        VStack {
            Image(systemName: "globe")
                .imageScale(.large)
                .foregroundStyle(.tint)
            Text("Hello, world!")
                .font(.largeTitle)
                .foregroundStyle(<#T##style: ShapeStyle##ShapeStyle#>)
        }
        .padding()
    }
}

#Preview {
    ContentView()
}
