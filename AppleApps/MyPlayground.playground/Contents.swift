import SwiftUI
import PlaygroundSupport

struct ExampleView: View {
    @State private var rotation: Double = 0
    var body: some View {
        VStack {
            Rectangle()
                .fill(Color.blue)
                .frame(width:200,
                       height:200)
