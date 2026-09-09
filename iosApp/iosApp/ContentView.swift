import SwiftUI
import composeApp

struct ContentView: View {
    var body: some View {
        ZStack {
            Color(red: 0xF4 / 255.0, green: 0xF4 / 255.0, blue: 0xF6 / 255.0)
                .ignoresSafeArea()

            ComposeView()
                .ignoresSafeArea(.keyboard)
        }
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        let controller = MainViewControllerKt.MainViewController()
        controller.view.backgroundColor = UIColor(red: 0xF4/255.0, green: 0xF4/255.0, blue: 0xF6/255.0, alpha: 1.0)
        return controller
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}