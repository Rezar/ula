//
//  HelpScreen.swift
//  iosApp
//
//  Created by Hao Wu on 8/24/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct HelpScreen: View {
    
    let faq: IOSFAQ
    let isExpanded: Bool
    
    var body: some View {
        
        HStack {
            
            content
            Spacer()
            
        }.contentShape(Rectangle())
        
    }
    
    private var content: some View {
        
        VStack(alignment: .leading) {
            
            Text(faq.question).font(.headline)
            
            if isExpanded {
                VStack(alignment: .leading) {
                    
                    // TODO: display different answer views after building each answer view
                    
//                    if faq.id == 1 {
//                        Answer1()
//                    }
//                    else if faq.id == 2 {
//                        Answer2()
//                    }
//                    else
//                    ...
                    Text(faq.answer)
                }
                
            }
            
        }
        
    }
    
}

//struct Answer1: View {
//
//}
//
//struct Answer2: View {
//
//}
//
//struct Answer3: View {
//
//}
//
//struct Answer4: View {
//
//}
//
//struct Answer5: View {
//
//}
//
//struct Answer6: View {
//
//}
