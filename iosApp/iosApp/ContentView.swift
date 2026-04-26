import SwiftUI

struct ContentView: View {
    @State var tapCount = 0
    
    var body: some View {
        Button("Tap count: \(tapCount)") {
            tapCount += 1
        }
    }
}

#Preview {
    ContentView()
}
