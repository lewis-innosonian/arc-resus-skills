import SwiftUI
import shared

struct ContentView: View {
    let message = MainBridge().getTestMessage()

    var body: some View {
       Text(message)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
       ContentView()
    }
}