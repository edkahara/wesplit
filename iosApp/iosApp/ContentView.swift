import SwiftUI

struct ContentView: View {
    @State private var name = ""
    
    var body: some View {
        Form {
            ForEach(0..<100) {
                Text("Row \($0)")
            }
        }
    }
}

#Preview {
    ContentView()
}
