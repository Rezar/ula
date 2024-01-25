//
//  HelpListScreen.swift
//  iosApp
//
//  Created by Hao Wu on 8/24/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared


struct HelpListScreen: View {
    
    let faqs: [IOSFAQ] = IOSFAQ.samples()
    @State private var selection: Set<IOSFAQ> = []
    
    var body: some View {
        
        ScrollView {
            
            Text("FAQs")
                .font(.AppTitle)
                .padding()

            ForEach(faqs) { f in

                HelpScreen(faq: f, isExpanded: self.selection.contains(f))
                    .modifier(ListRowModifier())
                    .onTapGesture { self.selectDeselect(f) }
                    .animation(.linear(duration: 0.3))

            }
            
        }
    }
    
    private func selectDeselect(_ faq: IOSFAQ) {
        if selection.contains(faq) {
            selection.remove(faq)
        } else {
            selection.insert(faq)
        }
    }
    
}



struct ListRowModifier: ViewModifier {
    func body(content: Content) -> some View {
        Group {
            content
            Divider()
        }.offset(x: 20)
    }
}


