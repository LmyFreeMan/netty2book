/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.phei.netty.basic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author lilinfeng
 * @date 2014年2月14日
 * @version 1.0
 */
public class TimeServerHandler extends ChannelHandlerAdapter {
    //读取客户端的内容发生的事件
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
	    throws Exception {
    //将msg对象转成ByteBuf
	ByteBuf buf = (ByteBuf) msg;
	//根据可读字节创建byte数组
	byte[] req = new byte[buf.readableBytes()];
	//将buf复制到req数组中
	buf.readBytes(req);
	//得到新的字符串
	String body = new String(req, "UTF-8");
	System.out.println("The time server receive order : " + body);
	String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(
		System.currentTimeMillis()).toString() : "BAD ORDER";
	ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
	//发送给客户端
	ctx.write(resp);
    }
	//读取客户端的内容完成后发生的事件
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
	ctx.flush();
    }
    //发生异常是的事件
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	ctx.close();
    }
}
